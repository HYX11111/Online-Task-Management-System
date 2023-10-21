package onlineSystem.core.staff;

import java.time.temporal.ChronoUnit;
import java.util.List;
import javax.persistence.*;

import onlineSystem.core.enums.RiskCode;
import onlineSystem.core.projectAndTasks.Project;
import onlineSystem.core.projectAndTasks.ProjectTask;

@Entity
public class Manager extends Staff {
    
    public Manager() {
    	super();
        this.position = "Manager";
    }

    //For Manager, only tasks with ORANGE or RED RiskCode will be displayed, and Manager has access to information regarding Projects in his or her department
    public String displayAlerts() {
        StringBuilder alertSB = new StringBuilder();
        List<Project> projects = staffAndProjectManagementService.getOngoingProjectsByDepartment(department);
        for(Project project : projects) {
            List<ProjectTask> tasks = project.getProjectTasks();
            //Sort the tasks by date 
            tasks.sort((ProjectTask task1, ProjectTask task2) -> (int) ChronoUnit.DAYS.between(task2.getDueDate(), task1.getDueDate()));
            for(ProjectTask task : tasks) {
                if(riskCodeAssessor.getRiskCode(task).equals(RiskCode.ORANGE) || riskCodeAssessor.getRiskCode(task).equals(RiskCode.RED)) {
                    alertSB.append(task.toString());
                }
            }
        }
        return alertSB.toString();
    }

    //Display Projects in his or her Department
    public String displayOngingProjects() {
        StringBuilder messageSB = new StringBuilder();
        List<Project> projects = staffAndProjectManagementService.getOngoingProjectsByDepartment(department);
        for(Project project : projects) { messageSB.append(project.toString()); }
        return messageSB.toString();
    }

     public String displayPreviousProjects() {
        StringBuilder messageSB = new StringBuilder();
        List<Project> projects = staffAndProjectManagementService.getPreviousProjectsByDepartment(department);
        for(Project project : projects) { messageSB.append(project.toString()); }
        return messageSB.toString();
    }
}


