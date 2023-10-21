package onlineSystem.web.controllers;

import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import onlineSystem.core.enums.Department;
import onlineSystem.core.projectAndTasks.Project;
import onlineSystem.core.projectAndTasks.ProjectTask;
import onlineSystem.core.staff.Employee;
import onlineSystem.core.staff.GeneralManager;
import onlineSystem.core.staff.Manager;
import onlineSystem.core.staff.Staff;
import onlineSystem.core.storingService.StaffAndProjectManagementService;

@Controller
@RequestMapping("/userPage")
public class UserPageController {

    @Autowired
	StaffAndProjectManagementService staffAndProjectManagementService;

    @GetMapping
    public String showUserPage(Model model, HttpSession session) {
        Staff staff = (Staff) session.getAttribute("staff");
        if(staff != null) {
	        model.addAttribute("staff", staff);
	        String msg = staff.displayAlerts().replace("\n", "<br>");
	        if(!msg.isEmpty()) {
	        	session.setAttribute("dueMsg", msg);
	        } else {
	        	session.setAttribute("dueMsg", "N/A");
	        }
	        return "userPage";
        } else {
        	return "signin";
        }
    }

    @PostMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }

    @PostMapping
    public String handleRequest(@ModelAttribute("project") Project project, HttpSession session,
            @RequestParam(value = "displayOngoingProjects", required = false) String displayOngoingProjects,
            @RequestParam(value = "displayPreviousProjects", required = false) String displayPreviousProjects,
            @RequestParam(value = "projectOrTask", required = false) String projectOrTask,
            @RequestParam(value = "IDField", required = false) String IDField,
            @RequestParam(value = "information", required = false) String information,
            @RequestParam(value = "department", required = false) String department,
            @RequestParam(value = "displayOngoingProjectsByDepartment", required = false) String displayOngoingProjectsByDepartment,
            @RequestParam(value = "displayPreviousProjectsByDepartment", required = false) String displayPreviousProjectsByDepartment,
            @RequestParam(value = "staffIDField", required = false) String staffIDField,
            @RequestParam(value = "displayOngoingProjectsByStaffID", required = false) String displayOngoingProjectsByStaffID,
            @RequestParam(value = "displayPreviousProjectsByStaffID", required = false) String displayPreviousProjectsByStaffID) {

        Staff staff = (Staff) session.getAttribute("staff");

        if (displayOngoingProjects != null) {
            String msg = staff.displayOngingProjects().replace("\n", "<br>");
            setMessage(msg, session);
            return "userPage";

        }  else if (displayPreviousProjects != null) {
            String msg = staff.displayPreviousProjects().replace("\n", "<br>");
            setMessage(msg, session);
            return "userPage";

        } else if (information != null) {
        	 int id;
            	try {
	                 id = Integer.parseInt(IDField);
	                 if(id < 0) {
	                     session.setAttribute("msg", "Please enter a valid Project or Task ID.<br>");
	                     return "userPage";
	                 }
                 } catch (NumberFormatException e) {
                	 session.setAttribute("msg", "Please enter a valid Project or Task ID.<br>");
                     return "userPage";
                }
        	
        	if(projectOrTask == null) {
                session.setAttribute("msg", "Please choose ProjectID or TaskID.<br>");
                return "userPage";
            }
        	
        	if(projectOrTask.equals("projectID")) {
                Project projectInquired = staffAndProjectManagementService.getProjectByProjectID(id);
                if(projectInquired != null) {
                    //Check to see whether the Staff has access to the information inquired.
                    if(staff instanceof Employee) {
                        if(staff.getId() == projectInquired.getStaff().getId()) {
                            setMessage(projectInquired.toString().replace("\n", "<br>"), session);
                        } else {
                            session.setAttribute("msg", "Sorry, you do not have access to the informaton inquired.<br>");
                        }
                    } else if(staff instanceof Manager) {
                        if(staff.getDepartment().equals(projectInquired.getDepartment())) {
                            setMessage(projectInquired.toString().replace("\n", "<br>"), session);
                        } else {
                            session.setAttribute("msg", "Sorry, you do not have access to the informaton inquired.<br>");
                        }
                    } else if(staff instanceof GeneralManager) {
                        setMessage(projectInquired.toString(), session);
                    }
                } else {
                    session.setAttribute("msg", "Please enter a valid Project or Task ID.<br>");
                }
                return "userPage";
            } else if(projectOrTask.equals("taskID")) {
                ProjectTask taskInquired = staffAndProjectManagementService.getTaskByTaskID(id);
                if(taskInquired != null) {
                    session.setAttribute("msg", taskInquired.toString().replace("\n", "<br>"));
                } else {
                    session.setAttribute("msg", "Please enter a valid Project or Task ID.<br>");
                }
                return "userPage";
            }

        } else if (displayOngoingProjectsByDepartment != null) {
            if(staff instanceof GeneralManager){
                String msg = ((GeneralManager) staff).displayOngoingProjectsByDepartment(Department.valueOf(department));
                setMessage(msg.replace("\n", "<br>"), session);
                return "userPage";
            }
        
        } else if (displayPreviousProjectsByDepartment != null) {
            if(staff instanceof GeneralManager){
                String msg = ((GeneralManager) staff).displayPreviousProjectsByDepartment(Department.valueOf(department));
                setMessage(msg.replace("\n", "<br>"), session);
                return "userPage";
            }

        } else if (displayOngoingProjectsByStaffID != null) {
            if(staff instanceof GeneralManager){
            	try {
                    int staffID = Integer.parseInt(staffIDField);
                    Staff staffInquired = staffAndProjectManagementService.getStaffById(staffID);
                    if(staffInquired == null) {
                    	session.setAttribute("msg", "Please enter a valid staffID.<br>");
                        return "userPage";
                    } else {
                    	String msg = ((GeneralManager) staff).displayOngoingProjectsByStaffID(staffID);
                setMessage(msg.replace("\n", "<br>"), session);
                return "userPage";
                    }
            	} catch (NumberFormatException e){
               	 session.setAttribute("msg", "Please enter a valid staffID.<br>");
                    return "userPage";
            	}
            }
         } else if (displayPreviousProjectsByStaffID != null) {
        	 if(staff instanceof GeneralManager){
             	try {
                     int staffID = Integer.parseInt(staffIDField);
                     Staff staffInquired = staffAndProjectManagementService.getStaffById(staffID);
                     if(staffInquired == null) {
                     	session.setAttribute("msg", "Please enter a valid staffID.<br>");
                         return "userPage";
                     } else {
                     	String msg = ((GeneralManager) staff).displayPreviousProjectsByStaffID(staffID);
                 setMessage(msg.replace("\n", "<br>"), session);
                 return "userPage";
                     }
             	} catch (NumberFormatException e){
                	 session.setAttribute("msg", "Please enter a valid staffID.<br>");
                     return "userPage";
             	}
             }
         }
        return "userPage";
    }
    
    void setMessage(String msg, HttpSession session) {
        if (msg != null && !msg.isEmpty()) {
                session.setAttribute("msg", msg);
        } else {
            session.setAttribute("msg", "No project or task meets the criteria.");
        };
    }
}
