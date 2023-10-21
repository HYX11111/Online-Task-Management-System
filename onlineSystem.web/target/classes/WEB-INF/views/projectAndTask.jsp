<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<title>Project and Task</title>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
    
    <script>
        function confirmAction(action) {
            var confirmation = confirm("Are you sure you want to " + action + "? This action cannot be undone.");
            if (confirmation) {
                return true;
            }
            return false;
        }
    </script>

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
				
				<form:form method="post" modelAttribute="project"> 
                    <span class="login100-form-title">
						Projects and Tasks
					</span><br>

                    <div> 
                    	<p>Register projects and tasks</p>     
                        <label for="projNameField">Project Name:</label>
                        <form:input type="text" name="projNameField" path = "projectName"/><br>

                        <label for="staffID">Staff ID:</label>
                        <input type="number" name="staffID"><br>
                        
                        <label for="departmentList">Select Department:</label>
                        <form:select id="departmentList" name="departmentList" path = "department">
                            <option value="ROAD_PROJECT">Road Project Department</option>
                            <option value="BUILDING_PROJECT">Building Project Department</option>
                            <option value="FINANCIAL_DEPARTMENT">Financial Department</option>
                        </form:select><br>
                        
                        <!-- Table for Project and Task Information -->
                        <table class="bordered-table">
                            <tr>
                                <th>Task Name</th>
                                <th>Project Value</th>
                                <th>Due Date</th>
                            </tr>
                            <c:forEach begin="0" end="4" varStatus="status">
                                <tr>
                                    <td>
                                        <input type="text" name="taskNames" />
                                    </td>
                                    <td>
                                        <input type="text" name="values" />
                                    </td>
                                    <td>
                                        <input type="date" name="dueDates" />
                                    </td>
                                </tr>
                            </c:forEach>
                        </table>
                        
                        <!-- Register Button -->
                        <input class="button_long" type="submit" value="Register project and tasks" name="register"><br><br>
                    </div> 

                    <!-- Project or Task ID Field -->
                    <div>
                    	<p>Look up and manage projects and tasks</p> 
                        <select name="projectOrTask" name = "projectOrTask">
                            <option value="projectID">Project ID</option>
                            <option value="taskID">TaskID</option>
                        </select>

                    <label for="IDField">ID:</label>
                    <input type="number" id="IDField" name="IDField"><br>
                    </div>

                    <!-- Buttons -->
                    <div>
                        <input class="button_long" type="submit" value="Display information" name="displayInformation">
                    </div>
                    <div>
                        <input class="button_long" type="submit" value="Mark as Completed" name="markCompleted" onclick="return confirmAction('mark this as completed');">
                        <input class="button_long" type="submit" value="Mark as Aborted" name="markAborted" onclick="return confirmAction('mark this as aborted');">
                    </div><br>

                    <!-- Info Text Area -->
                    <c:if test="${not empty msg}">
                        <div class="txt info">
                            <span>${msg}</span>
                        </div><br><br>
                    </c:if>
                    <c:remove var="msg"/>
                </form:form><br><br>
                
                <div class="text-center">
			        <a class="txt" href="registerStaff">
			            Register Staff
			        </a>
		    	</div>
            </div>
        </div>
    </div>

</body>
</html>

	