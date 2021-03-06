
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Register</title>

<script src="js/libs/jquery-3.5.1.js" type="text/javascript"></script>
<%@ include file="partials/header.jsp"%>

</head>
<body>
	<div class="register-form-div">
		<div class="card">
			<div class="card-header">Register</div>
			<div class="card-body">
				<form method="post" action="register">
					<div class="form-group">
						<input type="text"
							class="form-control" name="form-firstName"
							placeholder="First Name"
							value="<%=request.getAttribute("form-firstName")%>">
					</div>
					<div class="form-group">
						<input type="text"
							class="form-control" name="form-lastName" placeholder="Last Name"
							value="<%=request.getAttribute("form-lastName")%>">
					</div>
					<div class="form-group">
						<input type="text"
							class="form-control" name="form-username" placeholder="Username"
							value="<%=request.getAttribute("form-username")%>">
					</div>
					<div class="form-group">
						<input
							type="email" class="form-control" name="form-email"
							aria-describedby="emailHelp" placeholder="Enter email"
							value="<%=request.getAttribute("form-email")%>"> <small
							id="emailHelp" class="form-text text-muted">We'll never
							share your email with anyone else.</small>
					</div>
					<div class="form-group">
						<input type="password"
							class="form-control" name="form-password" placeholder="Password"
							value="<%=request.getAttribute("form-password")%>">
					</div>
					<button type="submit" class="btn btn-primary submit-button-registration">Submit</button>

				</form>
			</div>
			<div class="card-footer">
				<a href="login">
					<button class="btn btn-secondary move-button">Login</button>
				</a> <a href="main">
					<button class="btn btn-secondary move-button">Main Page</button>
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
		</div>




	</div>

	<%@ include file="partials/footer.jsp"%>
</body>
</html>