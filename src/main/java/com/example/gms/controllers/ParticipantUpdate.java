package com.example.gms.controllers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.example.gms.db.Database;
import com.example.gms.model.Participant;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ParticipantUpdate
 */
public class ParticipantUpdate extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ParticipantUpdate() {
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
		
		Participant participant = new Participant();
		Integer pid = Integer.parseInt(request.getParameter("pid"));
		participant.setPid(pid);
		participant.setName(request.getParameter("name"));
		participant.setPhone(request.getParameter("phone"));
		participant.setEmail(request.getParameter("email"));
		
		boolean pidExists = false;

		Database db = Database.getInstance();
		String checkIF_PID_unique = "SELECT COUNT(*) AS count FROM participant WHERE pid = ?";

		try (Connection connection = db.getConnection();
				PreparedStatement ps = connection.prepareStatement(checkIF_PID_unique)) {

			ps.setString(1, String.valueOf(participant.getPid()));

			try (ResultSet resultSet = db.executeQuery(ps)) {
				if (resultSet != null && resultSet.next()) {
					int count = resultSet.getInt("count");
					// turns boolean to true
					pidExists = count > 0;
				}
			}

			// Continue with the rest of your logic here
			if (pidExists) {
				// Handle the case where the pid already exists

				// SQL query to update participant data into the database
				String updateSql = "UPDATE participant SET name = ?, email = ?, phone = ? WHERE pid = ?";

				try (PreparedStatement ps2 = connection.prepareStatement(updateSql)) {

					// Set parameters for the participant insertion
					ps2.setString(1, participant.getName());
					ps2.setString(2, participant.getEmail());
					ps2.setString(3, participant.getPhone());
					ps2.setInt(4, participant.getPid());
					
					// Execute the update
					int result = db.executeUpdate(ps2);

					if (result > 0) {
						// Set attributes for data that the JSP will use to generate the view
						request.setAttribute("successMessage", "Participant updated successfully!");
						request.setAttribute("participantName", participant.getName());
			            request.setAttribute("participantPhone", participant.getPhone());
			            request.setAttribute("participantEmail", participant.getEmail());
			            request.setAttribute("participantBatchID", participant.getBid());

						// Forward the request to the JSP for rendering the view
						RequestDispatcher dispatcher = request.getRequestDispatcher("/add-participant.jsp");
						dispatcher.forward(request, response);
					} else {
						// Handle the case where no rows were found
					}

				} catch (Exception e) {
					e.printStackTrace();
				}

			} else {
				System.out.println("this PID does not exist, please navigate to AddParticipant module");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

}
