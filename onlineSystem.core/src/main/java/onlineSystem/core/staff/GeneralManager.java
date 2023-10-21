package onlineSystem.core.staff;

import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

import onlineSystem.core.enums.Department;
import onlineSystem.core.enums.RiskCode;
import onlineSystem.core.projectAndTasks.Project;
import onlineSystem.core.projectAndTasks.ProjectTask;

@Entity
public class GeneralManager extends Staff {
    
    public GeneralManager() {
    	super();
        this.position = "General Manager";
    }

    //For GeneralManager, only tasks with RED RiskCode will be displayed, and Manager has access to information regarding all Projects in the company
    public String displayAlerts() {
        StringBuilder alertSB = new StringBuilder();
        List<Project> projects = new ArrayList<Project>();

        for(Department department : Department.values()) {
            projects.addAll(staffAndProjectManagementService.getOngoingProjectsByDepartment(department));
        }
        for(Project project : projects) {
            List<ProjectTask> tasks = project.getProjectTasks();
            tasks.sort((ProjectTask task1, ProjectTask task2) -> (int) ChronoUnit.DAYS.between(task2.getDueDate(), task1.getDueDate()));
            for(ProjectTask task : tasks) {
                if(riskCodeAssessor.getRiskCode(task).equals(RiskCode.RED)) {
                    alertSB.append(task.toString());
                }
            }
        }
        return alertSB.toString();
    }
    
    public String displayOngingProjects() {
        StringBuilder messageSB = new StringBuilder();
        List<Project> projects = new ArrayList<Project>();

        for(Department department : Department.values()) {
            projects.addAll(staffAndProjectManagementService.getOngoingProjectsByDepartment(department));
        }
        for(Project project : projects) { 
            messageSB.append(project.toString()); 
        }
        return messageSB.toString();
    }

    public String displayPreviousProjects() {
        StringBuilder messageSB = new StringBuilder();
        List<Project> projects = new ArrayList<Project>();

        for(Department department : Department.values()) {
            projects.addAll(staffAndProjectManagementService.getPreviousProjectsByDepartment(department));
        }
        for(Project project : projects) { 
            messageSB.append(project.toString()); 
        }
        return messageSB.toString();
    }

    //Methods for GeneralManager to look up projects of a certain Staff or by Department
    public String displayOngoingProjectsByStaffID(int staffID) {
        StringBuilder messageSB = new StringBuilder();
        List<Project> projects = staffAndProjectManagementService.getOngoingProjectsByStaffID(staffID);
        for(Project project : projects) { 
            messageSB.append(project.toString()); 
        }
        return messageSB.toString();
    }

    public String displayPreviousProjectsByStaffID(int staffID) {
        StringBuilder messageSB = new StringBuilder();
        List<Project> projects = staffAndProjectManagementService.getPreviousProjectsByStaffID(staffID);
        for(Project project : projects) { 
            messageSB.append(project.toString()); 
        }
        return messageSB.toString();
    }

    public String displayOngoingProjectsByDepartment(Department department) {
        StringBuilder messageSB = new StringBuilder();
        List<Project> projects = staffAndProjectManagementService.getOngoingProjectsByDepartment(department);
        for(Project project : projects) { messageSB.append(project.toString()); }
        return messageSB.toString();
    }

    public String displayPreviousProjectsByDepartment(Department department) {
        StringBuilder messageSB = new StringBuilder();
        List<Project> projects = staffAndProjectManagementService.getPreviousProjectsByDepartment(department);
        for(Project project : projects) { messageSB.append(project.toString()); }
        return messageSB.toString();
    }
}