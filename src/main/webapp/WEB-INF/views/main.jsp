<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Home Page</title>


<%@ include file="partials/header.jsp"%>

</head>


<body onload="MainObject.navigationPage('homePage')">

	<nav class="navbar navbar-expand-lg navbar-light bg-light">
		<a class="navbar-brand" href="#">MiniFace</a>
		<button class="navbar-toggler" type="button" data-toggle="collapse"
			data-target="#navbarSupportedContent"
			aria-controls="navbarSupportedContent" aria-expanded="false"
			aria-label="Toggle navigation">
			<span class="navbar-toggler-icon"></span>
		</button>

		<div class="collapse navbar-collapse" id="navbarSupportedContent">
			<ul class="navbar-nav mr-auto">
				<li class="nav-item"><button class="nav-link nav-button" id="homePage" onclick="MainObject.navigationPage(this.id)">Home 
					<span class="sr-only">(current)</span>
				</button></li>
				
				<li class="nav-item"><button class="nav-link nav-button" id="showFriends" onclick="MainObject.navigationPage(this.id)">Friends</button></li>
				
				<li class="nav-item"><button class="nav-link nav-button" id="showGroups" onclick="MainObject.navigationPage(this.id)">Groups</button></li>

				<li class="nav-item dropdown">
						<button class="nav-link nav-button" id="showVissiblePosts" onclick="MainObject.navigationPage(this.id)">Posts</button>		
				</li>
				
		
			</ul>

			<a href="logout">
				<button class="btn btn-secondary submit-button-logout">Logout</button>
			</a>
		</div>
	</nav>

	<div id="primary" class="container"></div>
	<div id="secondary" class="container"></div>
	<div id="tertiary" class="container"></div>

	
	<%@ include file="partials/footer.jsp"%>


</body>
</html>