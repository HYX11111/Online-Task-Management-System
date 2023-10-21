<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>User Page</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    
    <link rel="stylesheet" type="text/css" href="css/main.css">
</head>

<body>
    <div class="limiter">
		<div class="container-login100">
			<div class="wrap-login100">
				<!-- Log Out Button -->
		        <form class="text-right" action="signout" method="get">
		            <button type="submit">Log out</button>
		        </form>
			        
				 <div>
			        <p>The following tasks are due:</p>
			    </div>
			
			     <!-- Info Text Area -->
		        <div class="textarea">
			         <c:if test="${not empty dueMsg}">
			            <span class="txt">${dueMsg}</span>
			         </c:if>
		        </div><br>
			
                <form method="post">
                    <div>
                        <!-- Display All Ongoing Projects Button -->
                        <button class="button_long" type="submit" name="displayOngoingProjects">Display all ongoing projects</button>

                        <!-- Display All Previous Projects Button -->
                        <button class="button_long" type="submit" name="displayPreviousProjects">Display all completed or aborted projects</button>
                    </div><br>

                    <div>
                        <label for="projectOrTask">Project or Task ID:</label>
                        <select id="projectOrTask" name="projectOrTask">
                            <option value="projectID">Project ID</option>
                            <option value="taskID">Task ID</option>
                        </select>
                                    
                        <!-- Project or Task ID Field -->
                        <label for="IDField">ID:</label>
                        <input type="number" id="IDField" name="IDField"><br>

                        <!-- Display Button -->
                        <input class="button_long" type="submit" value="Display information" name="information">
                    </div><br>
                    
                    <!-- Additional methods for General Manager-->
                    <c:if test="${staff.position == 'General Manager'}">
                        <div>
                            <label for="departmentList">Select Department:</label>
                            <select id="departmentList" name="department">
                                <option value="ROAD_PROJECT">Road Project Department</option>
                                <option value="BUILDING_PROJECT">Building Project Department</option>
                                <option value="FINANCIAL_DEPARTMENT">Financial Department</option>
                            </select>
                            <button class="button_long"  type="submit" name="displayOngoingProjectsByDepartment">Display ongoing projects by department</button>
                            <button class="button_long"  type="submit" name="displayPreviousProjectsByDepartment">Display completed or aborted by department</button>
                        </div><br>

                        <div>
                            <label for="staffID">Please enter staffID:</label>
                            <input type="text" name="staffIDField" id="staffID">
                            <button class="button_long"  type="submit" name="displayOngoingProjectsByStaffID">Display ongoing projects by staff ID</button>
                            <button class="button_long" type="submit" name="displayPreviousProjectsByStaffID">Display completed or aborted by staff ID</button>
                        </div><br>
                    </c:if>

                    <!-- Info Text Area -->
                    <c:if test="${not empty msg}">
                        <div class="info textarea">
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
