<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

<head>
	<meta charset="utf-8">
	<title>error</title>
	
	<!-- Google font -->
	<link href="https://fonts.googleapis.com/css?family=Montserrat:200,400,700" rel="stylesheet">
	<!-- Custom stlylesheet -->
	<link type="text/css" rel="stylesheet" href="css/notfound.css" />
</head>

<body>

	<div id="notfound">
		<div class="notfound">
			<div class="notfound-404">
				<h1>Ooooops... An error occured :(</h1>
					<p>
						Exception: <b>${exception}</b>
					</p>
					<p>
						Handler: <b> ${handler} </b>
					</p>
			</div>
			<a href="signin">Log In</a>
		</div>
	</div>

</body>
</html>