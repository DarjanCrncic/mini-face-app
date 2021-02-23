<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Home Page</title>

<script src="js/libs/jquery-3.5.1.js" type="text/javascript"></script>
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
				
				<li class="nav-item"><a class="nav-link" href="#">Groups</a></li>

				<li class="nav-item dropdown">
						<button class="nav-link nav-button" id="showVissiblePosts" onclick="MainObject.navigationPage(this.id)">Posts</button>		
				</li>
				
				
				<li class="nav-item dropdown"><a
					class="nav-link dropdown-toggle" href="#"
					id="navbarDropdownMenuLink" data-toggle="dropdown"
					aria-haspopup="true" aria-expanded="false"> Actions </a>
					<div class="dropdown-menu" aria-labelledby="navbarDropdownMenuLink">
						<button type="button" class="dropdown-item" data-toggle="modal" data-target="#exampleModalLong">Create Post</button> 
						<a class="dropdown-item" href="#">Another action</a> 
						<a class="dropdown-item" href="#">Something else here</a>

					</div></li>

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

	<script src="js/fragments/main.js" type="text/javascript"></script>
</body>
</html>