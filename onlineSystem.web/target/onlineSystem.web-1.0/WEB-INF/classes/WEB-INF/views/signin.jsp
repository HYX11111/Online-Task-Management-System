<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
	<title>Sign In</title>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	
	<link rel="stylesheet" type="text/css" href="css/main.css">
</head>

<body>
	<div class="limiter">
		<div class="container-login100">
			<div class="wrap-login100">
				<form class="login100-form" action="signin" method="POST">
					<span class="login100-form-title">
						Welcome
					</span><br><br>

					<div class="wrap-input100">
						<input class="input100" type="number" name="staffID" placeholder="Staff ID">
						<span class="focus-input100"></span>
					</div>

					<div class="wrap-input100">
						<input class="input100" type="password" name="password" placeholder="Password">
						<span class="focus-input100"></span>
					</div>

					<div class="container-login100-form-btn">
						<div class="wrap-login100-form-btn">
							<div class="login100-form-bgbtn">
								<button class="login100-form-btn">
									Sign In
								</button>
							</div>
						</div>
					</div><br>

					<c:if test="${msg != null}">
						<div class="text">
							<span class="txt">${msg}</span>
						</div>
					</c:if>
					<c:remove var="msg"/>
				</form>	
			</div>
		</div>
	</div>
</body>
</html>

<!-- Source: the css and jsp pages used parts of the template from 
https://www.codingnepalweb.com/free-login-registration-form-html-css/ -->