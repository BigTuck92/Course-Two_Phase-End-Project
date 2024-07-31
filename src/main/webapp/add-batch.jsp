<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<html>
<head>
<title>Batch View</title>
</head>
<body>

	<h2>Batch View</h2>

	<%-- Display a success message if available --%>
	<% if (request.getAttribute("successMessage") != null) { %>
	<p style="color: green;"><%= request.getAttribute("successMessage") %></p>
	<% } %>

	<%-- Your HTML content here, displaying participant data, etc. --%>
	<p><strong>Batch Name:</strong> <%= request.getAttribute("BatchName") %></p>
	<p><strong>Batch Time:</strong> <%= request.getAttribute("BatchTime") %></p>
	
	<br>
	<a href="index.html">Go back to index</a>

</body>
</html>
	
	
			
				