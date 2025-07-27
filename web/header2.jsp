<%@page import="in.gadgethub.util.AppInfo"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html>
	<head>
		<title>Logout Header</title>
		<meta charset="utf-8" />
		<meta name="viewport" content="width=device-width, initial-scale=1" />

		<link
			href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
			rel="stylesheet"
		/>

		<link rel="stylesheet" href="mycss.css" />
		<link
			rel="stylesheet"
			href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css"
		/>
	</head>
	<body style="background-color: #e6f9e6">
		<% /* Checking the user credentials for guest user */ /* Java Code */ %>

		<!-- Starting Navigation Bar -->
		<nav class="navbar navbar-expand-lg navbar-custom">
			<div class="container-fluid">
				<a class="navbar-brand" href="LandingServlet">
                                    <%=AppInfo.appName%>
				</a>
				<button
					class="navbar-toggler"
					type="button"
					data-bs-toggle="collapse"
					data-bs-target="#navbarNav"
					aria-controls="navbarNav"
					aria-expanded="false"
					aria-label="Toggle navigation"
				>
					<span class="navbar-toggler-icon"></span>
				</button>
				<div class="collapse navbar-collapse" id="navbarNav">
					<ul class="navbar-nav ms-auto">
						<!--Category JSP file-->
						<jsp:include page="category.jsp" />
						<li class="nav-item">
							<a class="nav-link" href="LandingServlet">Products</a>
						</li>
						<li class="nav-item">
							<a class="nav-link" href="login.jsp">Login</a>
						</li>
						<li class="nav-item">
							<a class="nav-link" href="register.jsp">Register</a>
						</li>
					</ul>
				</div>
			</div>
		</nav>
	

		<!-- End of Navigation Bar -->

		<!--Company Header Starting  -->
		<div
			class="container-fluid text-center bg-warning mt-0 p-3"
			style="--bs-bg-opacity: 0.5"
		>
                    <h2><%=AppInfo.appName%></h2>
			<h6>We specialize in Electronics</h6>
			<form class="form-inline" action="LandingServlet" method="get">
				<div class="input-group">
					<input
						type="text"
						class="form-control"
						name="search"
						placeholder="Search Items"
						required
					/>

					<input type="submit" class="btn btn-primary" value="Search" />
				</div>
			</form>
			<p
				align="center"
				style="
					color: blue;
					font-weight: bold;
					margin-top: 5px;
					margin-bottom: 5px;
				"
				id="message"
			>
				hello
			</p>
		</div>
		<!-- Company Header Ending -->

		<script
			src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
			integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz"
			crossorigin="anonymous"
		></script>
	</body>
</html>
