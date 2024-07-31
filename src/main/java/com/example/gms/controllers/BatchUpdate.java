package com.example.gms.controllers;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.example.gms.db.Database;
import com.example.gms.model.Batch;

/**
 * Servlet implementation class BatchUpdate
 */
public class BatchUpdate extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BatchUpdate() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Batch batch = new Batch();
		Integer bid = Integer.parseInt(request.getParameter("bid"));
		batch.setBid(bid);
		batch.setName(request.getParameter("name"));
		batch.setTime(request.getParameter("time"));
		
		boolean bidExists = false;

		Database db = Database.getInstance();
		String checkIF_BID_unique = "SELECT COUNT(*) AS count FROM batch WHERE bid = ?";

		try (Connection connection = db.getConnection();
				PreparedStatement ps = connection.prepareStatement(checkIF_BID_unique)) {

			ps.setString(1, String.valueOf(batch.getBid()));

			try (ResultSet resultSet = db.executeQuery(ps)) {
				if (resultSet != null && resultSet.next()) {
					int count = resultSet.getInt("count");
					// turns boolean to true
					bidExists = count > 0;
				}
			}

			// Continue with the rest of your logic here
			if (bidExists) {
				// Handle the case where the pid already exists

				// SQL query to update participant data into the database
				String updateSql = "UPDATE batch SET name = ?, time = ? WHERE bid = ?";

				try (PreparedStatement ps2 = connection.prepareStatement(updateSql)) {

					// Set parameters for the participant insertion
					ps2.setString(1, batch.getName());
					ps2.setString(2, batch.getTime());
					ps2.setInt(3, batch.getBid());
					
					// Execute the update
					int result = db.executeUpdate(ps2);

					if (result > 0) {
						// Set attributes for data that the JSP will use to generate the view
						request.setAttribute("successMessage", "Batch Updated Successfully!");
						request.setAttribute("BatchName", batch.getName());
						request.setAttribute("BatchTime", batch.getTime());

						// Forward the request to the JSP for rendering the view
						RequestDispatcher dispatcher = request.getRequestDispatcher("/add-batch.jsp");
						dispatcher.forward(request, response);
					} else {
						// Handle the case where no rows were found
					}

				} catch (Exception e) {
					e.printStackTrace();
				}

			} else {
				System.out.println("this BID does not exist, please navigate to CreateBatch module");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

}
