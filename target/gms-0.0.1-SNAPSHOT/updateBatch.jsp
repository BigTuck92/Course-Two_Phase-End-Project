<%@page import="com.example.gms.db.Database"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.PreparedStatement"%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!doctype html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Update Batch</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH"
	crossorigin="anonymous">
</head>
<style>
.background {
	background-color: black;
}

h3 {
	color: white;
	text-align: center;
}

.grid {
	display: grid;
}

.form {
	text-align: center;
	width: 25%;
	padding: 16px;
	background-color: rgb(243, 234, 181);
	border-radius: 12px;
	box-shadow: 0 4px 8px 0 rgba(255, 255, 255, 0.8);
	margin-left: auto;
	margin-right: auto;
}

.input {
	width: 90%;
	display: inline;
	background-color: rgb(213, 237, 245);
	border: 2px solid black;
	border-radius: 6px;
	box-sizing: content-box;
}

.btn-outline-secondary {
	width: 40%;
	display: inline;
	margin: 5px;
}
</style>

<body class="background">
	<nav class="navbar navbar-expand-lg bg-body-tertiary">
		<div class="container-fluid">
			<a class="navbar-brand" href="#">Navbar</a>
			<button class="navbar-toggler" type="button"
				data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent"
				aria-controls="navbarSupportedContent" aria-expanded="false"
				aria-label="Toggle navigation">
				<span class="navbar-toggler-icon"></span>
			</button>
			<div class="collapse navbar-collapse" id="navbarSupportedContent">
				<ul class="navbar-nav me-auto mb-2 mb-lg-0">
					<li class="nav-item"><a class="nav-link"
						href="createBatch.html">Create a Batch</a></li>
					<li class="nav-item"><a class="nav-link"
						href="createParticipant.html">Create a Participant</a></li>
					<li class="nav-item"><a class="nav-link" href="updateBatch.jsp">Update a
							Batch</a></li>
					<li class="nav-item"><a class="nav-link"
						href="updateParticipant.jsp">Update a Participant</a></li>
				</ul>
			</div>
		</div>
	</nav>


	<%
	try {
		// Establish a connection with the mysql database
		Database db = Database.getInstance();
		String sql = "select batch.bid, batch.name, batch.time from batch";
		PreparedStatement ps = db.getConnection().prepareStatement(sql);
		ResultSet rs = db.executeQuery(ps);
		if (rs.next() == false) {
			out.println("No records found in the tables");
		} else {
	%>
	<table class="table table-hover">
		<thead>
			<tr>
				<th scope="col">Batch ID</th>
				<th scope="col">Batch Name</th>
				<th scope="col">Batch Time</th>
			</tr>
		</thead>
		<tbody>
			<%
			do {
			%>
			<tr>
				<td><%=rs.getString(1)%></td>
				<td><%=rs.getString(2)%></td>
				<td><%=rs.getString(3)%></td>
			</tr>
			<%
			} while (rs.next());
			%>
		</tbody>
	</table>
	<%
	}

	} catch (Exception e) {
	e.getStackTrace();
	}
	%>
	<h3>Please enter the updated details below:</h3>
	<div>
        <form class="form" action="BatchUpdate" method="post">
            <div class="mb-3">
                <label for="bid" class="form-label">Batch ID to Update:</label>
                <input type="number" class="form-control input" name="bid" id="bid" required>
            </div>
            <div class="mb-3">
                <label for="name" class="form-label">Name</label>
                <input type="text" class="form-control input" name="name" id="name" required>
            </div>
            <div class="mb-3">
              <label for="time" class="form-label">Time</label>
              <input type="time" class="form-control input" name="time" id="time" required>
            </div>
            <button type="submit" class="btn btn-primary">Update</button>
          </form>
    </div>
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
		integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz"
		crossorigin="anonymous"></script>
</body>
</html>