package onlineSystem.web.controllers;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import onlineSystem.core.Administrator;
import onlineSystem.core.enums.Department;
import onlineSystem.core.projectAndTasks.ConstructionTask;
import onlineSystem.core.projectAndTasks.PaymentTask;
import onlineSystem.core.projectAndTasks.Project;
import onlineSystem.core.projectAndTasks.ProjectTask;
import onlineSystem.core.staff.Staff;
import onlineSystem.core.storingService.StaffAndProjectManagementService;

@Controller
@RequestMapping("/projectAndTask")
public class ProjectAndTaskController {
    
    @Autowired
	StaffAndProjectManagementService staffAndProjectManagementService;

	@GetMapping
	public String doGet(HttpSession session, Model model) {
		//Check whether the user has access	    
		Object admin = session.getAttribute("Admin");
		System.out.println("admin:" + admin);
		if(admin == null || !(admin instanceof Administrator)) {
			return "redirect:/signin";
		}
		
		
		Project project = new Project();
		model.addAttribute("project", project);
		return "projectAndTask";
	}
	
	@PostMapping
    public String handleRequest(@ModelAttribute("project") Project project, HttpSession session,
    						@RequestParam(value ="staffID", required = false) String staffIDField,
			    		    @RequestParam(value ="taskNames", required = false) String[] taskNames,
			    		    @RequestParam(value ="values", required = false) String[] values,
			    		    @RequestParam(value ="dueDates", required = false) String[] dueDates,
                            @RequestParam(value = "register", required = false) String register,
                            @RequestParam(value = "projectOrTask", required = false) String projectOrTask,
                            @RequestParam(value = "IDField", required = false) String IDField,
                            @RequestParam(value = "displayInformation", required = false) String displayInformation,
                            @RequestParam(value = "markCompleted", required = false) String markCompleted,
                            @RequestParam(value = "markAborted", required = false) String markAborted) {

		//Register projects and tasks
        if (register != null) {
            //Check to see whether a valid staffID is entered
        	try {
                int staffID = Integer.parseInt(staffIDField);
                Staff staff = staffAndProjectManagementService.getStaffById(staffID);
                if(staff == null) {
                	session.setAttribute("msg", "Please enter a valid staffID.<br>");
                    return "projectAndTask";
                }
                if(!staff.getDepartment().equals(project.getDepartment())) {
                	session.setAttribute("msg", "The responsible staff and the department do not match.<br>");
                    return "projectAndTask";
                }
                project.setStaff(staff);
            } catch (NumberFormatException e){
            	 session.setAttribute("msg", "Please enter a valid staffID.<br>");
                 return "projectAndTask";
            }
            
            //Register tasks
            List<ProjectTask> tasks = new ArrayList<>();
            for (int i = 0; i < taskNames.length; i++) {
                if (!taskNames[i].isEmpty() && !values[i].isEmpty() && !dueDates[i].isEmpty()) {
                    String taskName = taskNames[i];
                    try {
                        double projectValue = Double.parseDouble(values[i]);
                        LocalDate dueDate = LocalDate.parse(dueDates[i]);
                        ProjectTask task;
                        //Determine type of task depending on the department
                        if (project.getDepartment() == Department.FINANCIAL_DEPARTMENT) {
                            task = new PaymentTask(project, taskName, projectValue, dueDate);
                        } else {
                            task = new ConstructionTask(project, taskName, projectValue, dueDate);
                        }
                        tasks.add(task);   
                    } catch (NumberFormatException | DateTimeParseException e) {
                    	 session.setAttribute("msg", "Please enter project and task information correctly.<br>");
                         return "projectAndTask";
                    }
                }
            }
            
            if(!tasks.isEmpty()) {
	            Collections.sort(tasks, Comparator.comparing(ProjectTask::getDueDate));
	            project.setTasks(tasks);
	            String msg = staffAndProjectManagementService.saveProject(project).replace("\n", "<br>");
	            session.setAttribute("msg", "New Project registered: <br>" + msg + "Please remember Project information.<br>");
	            return "projectAndTask";
            } else {
            	 session.setAttribute("msg", "Please enter project and task information correctly.<br>");
                 return "projectAndTask";
            }
            
        //Look up project information
        } else if (displayInformation != null) {
            if(projectOrTask == null) {
                session.setAttribute("msg", "Please choose ProjectID or TaskID.<br>");
                return "projectAndTask";
            }

            int id;
           	try {
                id = Integer.parseInt(IDField);
                if(id < 0) {
                    session.setAttribute("msg", "Please enter a valid Project or Task ID.<br>");
                    return "projectAndTask";
                }
            } catch (NumberFormatException e){
            	 session.setAttribute("msg", "Please enter a valid Project or Task ID.<br>");
                 return "projectAndTask";
            }
            
            if(projectOrTask.equals("projectID")) {
                Project projectInquired = staffAndProjectManagementService.getProjectByProjectID(id);
                if(projectInquired != null) {
                    session.setAttribute("msg", projectInquired.toString().replace("\n", "<br>"));
                } else {
                    session.setAttribute("msg", "Please enter a valid Project or Task ID.<br>");
                }
            } else if(projectOrTask.equals("taskID")) {
                ProjectTask taskInquired = staffAndProjectManagementService.getTaskByTaskID(id);
                if(taskInquired != null) {
                    session.setAttribute("msg", taskInquired.toString().replace("\n", "<br>"));
                } else {
                    session.setAttribute("msg", "Please enter a valid Project or Task ID.<br>");
            }
            return "projectAndTask";
            }

        //Mark project or task as aborted/completed
        } else if (markAborted != null || markCompleted != null) {
             if(projectOrTask == null) {
                session.setAttribute("msg", "Please choose ProjectID or TaskID.<br>");
                return "projectAndTask";
            }

            int id;
         	try {
                id = Integer.parseInt(IDField);
                if(id < 0 || IDField == null || IDField.isEmpty()) {
                    session.setAttribute("msg", "Please enter a valid Project or Task ID.<br>");
                    return "projectAndTask";
                }
            } catch (NumberFormatException e){
            	 session.setAttribute("msg", "Please enter a valid Project or Task ID.<br>");
                 return "projectAndTask";
            }

            if(projectOrTask.equals("projectID")) {
                Project projectInquired = staffAndProjectManagementService.getProjectByProjectID(id);
                if(projectInquired != null) {
                    if(markAborted != null) {
                        projectInquired.markAsAborted();
                        session.setAttribute("msg", "Project marked as aborted:<br>" + staffAndProjectManagementService.updateProject(projectInquired).replace("\n", "<br>"));
                    } else if (markCompleted != null) {
                    	projectInquired.markAsCompleted();
                        session.setAttribute("msg", "Project marked as completed:<br>" + staffAndProjectManagementService.updateProject(projectInquired).replace("\n", "<br>"));
                    }
                } else {
                    session.setAttribute("msg", "Please enter a valid Project or Task ID.<br>");
                }
                return "projectAndTask";
            } else if(projectOrTask.equals("taskID")) {
                ProjectTask taskInquired = staffAndProjectManagementService.getTaskByTaskID(id);
                if(taskInquired != null) {
                   if(markAborted != null) {
                        taskInquired.markAsAborted();
                        session.setAttribute("msg", "Task marked as aborted:<br>" + staffAndProjectManagementService.updateTask(taskInquired).replace("\n", "<br>"));
                    } else if (markCompleted != null) {
                        taskInquired.markAsCompleted();
                        session.setAttribute("msg", "Task marked as completed:<br>" + staffAndProjectManagementService.updateTask(taskInquired).replace("\n", "<br>"));
                    }
                } else {
                    session.setAttribute("msg", "Please enter a valid Project or Task ID.<br>");
                }
                return "projectAndTask";
            }
        }
        return "projectAndTask";
    }
}
