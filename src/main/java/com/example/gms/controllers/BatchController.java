package com.example.gms.controllers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.example.gms.db.Database;
import com.example.gms.model.Batch;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class BatchController
 */
public class BatchController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public BatchController() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Batch batch = new Batch();
		batch.setName(request.getParameter("name"));
		batch.setTime(request.getParameter("time"));

		Database db = Database.getInstance();

		boolean nameExist = false;

		String checkIFBatchName_unique = "SELECT COUNT(*) AS count FROM Batch WHERE name = ?";

		try (Connection connection = db.getConnection();
				PreparedStatement ps = connection.prepareStatement(checkIFBatchName_unique)) {

			ps.setString(1, batch.getName());

			try (ResultSet resultSet = db.executeQuery(ps)) {
				if (resultSet != null && resultSet.next()) {
					int count = resultSet.getInt("count");
					// turns boolean to true
					nameExist = count > 0;
				}

			}

			// Check if the combination already exists in the database
			// Remember, if (!exists) is equivalent to saying "if exists is not true" or "if
			// the combination does not exist in the database."

			if (!nameExist) {
				// Execute the SQL insert statement

				// SQL query to insert participant data into the database
				String sql = "INSERT INTO batch (name, time) VALUES (?, ?)";

				try (PreparedStatement ps2 = connection.prepareStatement(sql)) {

					// Set parameters for the participant insertion
					ps2.setString(1, batch.getName());
					ps2.setString(2, batch.getTime());

					// Execute the update
					int result = db.executeUpdate(ps2);

					if (result > 0) {
						// Set attributes for data that the JSP will use to generate the view
						request.setAttribute("successMessage", "NewBatch added successfully!");
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
				// Handle the case where the combination already exists
				System.out.println("Batch with the same Name already exists.");
			}

			// Implement the batchExists and insertBatch methods accordingly

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

}
