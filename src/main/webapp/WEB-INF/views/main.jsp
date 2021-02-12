<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Home Page</title>

<%@ include file="partials/header.jsp"%>

</head>


<body>
	<div class="main-body-div">
		<h1>MAIN PAGE</h1>

		<a href="logout">
			<button class="btn btn-secondary submit-button">Logout</button>
		</a>
	</div>

	<%@ include file="partials/footer.jsp"%>
</body>
</html>