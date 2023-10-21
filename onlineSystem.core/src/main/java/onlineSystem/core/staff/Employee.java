package onlineSystem.core.staff;

import java.time.temporal.ChronoUnit;
import java.util.List;
import javax.persistence.*;

import onlineSystem.core.enums.RiskCode;
import onlineSystem.core.projectAndTasks.Project;
import onlineSystem.core.projectAndTasks.ProjectTask;

@Entity
public class Employee extends Staff {
    
    public Employee () {
    	super();
        this.position = "Employee";
    }

    //For Employee, all overdue tasks with YELLOW, ORANGE or RED RiskCode will be displayed, and Employee only has access to information regarding his or her Projects
    public String displayAlerts() {
        StringBuilder alertSB = new StringBuilder();
        List<Project> projects = staffAndProjectManagementService.getOngoingProjectsByStaffID(s_id);
        for(Project project : projects) {
            List<ProjectTask> tasks = project.getProjectTasks();
            //Sort the Tasks by dueDate so that the undue Tasks of each Project will be displayed by the chronological order
            tasks.sort((ProjectTask task1, ProjectTask task2) -> (int) ChronoUnit.DAYS.between(task2.getDueDate(), task1.getDueDate()));
            for(ProjectTask task : tasks) {
                if(riskCodeAssessor.getRiskCode(task).equals(RiskCode.YELLOW) || riskCodeAssessor.getRiskCode(task).equals(RiskCode.ORANGE) || riskCodeAssessor.getRiskCode(task).equals(RiskCode.RED)) {
                    alertSB.append(task.toString());
                }
            }
        }
        return alertSB.toString();
    }

    //Display his or her Projects
    public String displayOngingProjects() {
        StringBuilder messageSB = new StringBuilder();
        List<Project> projects = staffAndProjectManagementService.getOngoingProjectsByStaffID(s_id);
        for(Project project : projects) { messageSB.append(project.toString()); }
        return messageSB.toString();
    }

    public String displayPreviousProjects() {
        StringBuilder messageSB = new StringBuilder();
        List<Project> projects = staffAndProjectManagementService.getPreviousProjectsByStaffID(s_id);
        for(Project project : projects) { messageSB.append(project.toString()); }
        return messageSB.toString();
    }
}

