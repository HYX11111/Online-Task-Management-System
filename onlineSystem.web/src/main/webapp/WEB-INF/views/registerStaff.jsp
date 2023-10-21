<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<title> Register Staff</title>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	
	<link rel="stylesheet" type="text/css" href="css/main.css">
</head>
<body>	
	<div class="limiter">
		<div class="container-login100">
			<div class="wrap-login100">
				<div>
			         <!-- Log Out Button -->
			        <form class="text-right" action="signout" method="get">
			            <button type="submit">Log out</button>
			        </form>
			    </div>
        
				<form:form class="login100-form" action="registerStaff" modelAttribute="staff" method="POST">
					<span class="login100-form-title">
						Register Staff 
					</span><br><br>
					
					<div class="wrap-input100">
						<form:input class="input100" path="staffName" placeholder="Staff Name"/>
						<span class="focus-input100"></span>
					</div>
					
					<div class="wrap-input100">
						<form:select class="input100" path="department">
							<form:option value="" label="Select Department"/>
							<form:option value="ROAD_PROJECT">Road Project</form:option>
							<form:option value="BUILDING_PROJECT">Building Project</form:option>
							<form:option value="FINANCIAL_DEPARTMENT">Finalcial Department</form:option>
							<form:option value="MANAGEMENT">Management</form:option>
						</form:select>
					</div>
				
					<div class="wrap-input100">
						<form:select class="input100" path="position">
							<form:option value="" label="Select Position"/>
							<form:option value="Employee">Employee</form:option>
							<form:option value="Manager">Manager</form:option>
							<form:option value="General Manager">General Manager</form:option>
						</form:select>
					</div>

					<div class="wrap-input100">
						<form:input class="input100" path="password" placeholder="Password"/>
						<span class="focus-input100"></span>
					</div>
					
					<div class="wrap-input100">
						<input class="input100 has-val" name="repeatPassword" placeholder="Repeat Password"/>
						<span class="focus-input100"></span>
					</div>

					<div>
						<button class="button_long">
							Register
						</button>
					</div><br>
					
					<!-- Info Text Area -->
					<c:if test="${not empty msg}">
						<div>
							<span class="txt">${msg}</span>
						</div><br><br>
					</c:if>
					<c:remove var="msg"/>
				</form:form><br><br>
				
				<div class="text-center">
					<a class="txt" href="projectAndTask">
						Check and register projects and tasks
					</a>
				</div>
			</div>
		</div>
	</div>
</body>
</html>