package com.example.gms.controllers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

import com.example.gms.db.Database;
import com.example.gms.model.Participant;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ParticipantController
 */
public class ParticipantController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ParticipantController() {
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

		// Create a participant object and set its properties
		Participant participant = new Participant();
		participant.setName(request.getParameter("name"));
		participant.setPhone(request.getParameter("phone"));
		participant.setEmail(request.getParameter("email"));
		Integer bid = Integer.parseInt(request.getParameter("bid"));
		participant.setBid(bid);

		// Use the database singleton instance
		Database db = Database.getInstance();

		// SQL query to insert participant data into the database
		String sql = "INSERT INTO Participant (name, phone, email, bid) VALUES (?, ?, ?, ?)";

		try (Connection connection = db.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {

			// Set parameters for the participant insertion
			ps.setString(1, participant.getName());
			ps.setString(2, participant.getPhone());
			ps.setString(3, participant.getEmail());
			ps.setInt(4, participant.getBid());

			// Execute the update
			int result = db.executeUpdate(ps);
			
			if (result > 0) {
	            // Set attributes for data that the JSP will use to generate the view
	            request.setAttribute("successMessage", "Participant added successfully!");
	            request.setAttribute("participantName", participant.getName());
	            request.setAttribute("participantPhone", participant.getPhone());
	            request.setAttribute("participantEmail", participant.getEmail());
	            request.setAttribute("participantBatchID", participant.getBid());
	            
	            // Forward the request to the JSP for rendering the view
	            RequestDispatcher dispatcher = request.getRequestDispatcher("/add-participant.jsp");
	            dispatcher.forward(request, response);
	        } else {
	            System.out.println("Participant not Inserted into DB...");
	        }

		} catch (Exception e) {
			System.out.println("Exception Occured: " + e);
			e.printStackTrace();
		}

	}

}
