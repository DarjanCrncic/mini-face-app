<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Login</title>

<%@ include file="partials/header.jsp"%>

</head>
<body>
	<div class="login-form-div">
		<form method="post" action="login">
			<div class="form-group">
				<label for="form-username">Username</label> <input type="text"
					class="form-control" name="form-username" placeholder="Username">
			</div>
			<div class="form-group">
				<label for="form-password">Password</label> <input type="password"
					class="form-control" name="form-password" placeholder="Password">
			</div>
			<button type="submit" class="btn btn-primary submit-button">Submit</button>
		</form>

		<a href="register">
			<button class="btn btn-secondary submit-button">Register</button>
		</a>
		
		<a href="main">
			<button class="btn btn-secondary submit-button">Main Page</button>
		</a>
		
		<div class="error-message-div">
			<%
			if (request.getAttribute("errorMessages") != null) {
			%>
			<ul>
				<%
				String[] errors = (String[]) request.getAttribute("errorMessages");
				for (String error : errors) {
				%>
				<li class="error"><%=error%></li>
				<%
				}
				%>
			</ul>
			<%
			}
			%>
		</div>
	</div>
	
	

	<%@ include file="partials/footer.jsp"%>

</body>
</html>