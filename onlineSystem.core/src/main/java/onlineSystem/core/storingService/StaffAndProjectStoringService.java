package onlineSystem.core.storingService;

import java.util.List;

import onlineSystem.core.enums.Department;
import onlineSystem.core.projectAndTasks.Project;
import onlineSystem.core.projectAndTasks.ProjectTask;
import onlineSystem.core.staff.Staff;

public interface StaffAndProjectStoringService {
    
    String saveStaff(Staff staff);
 
    Staff getStaffById(Integer staffID);

    Project getProjectByProjectID(int projectID);

    ProjectTask getTaskByTaskID(int taskID);

    String saveProject(Project project);

    List<Project> getOngoingProjectsByDepartment(Department department);

    List<Project> getPreviousProjectsByDepartment(Department department);

    List<Project> getOngoingProjectsByStaffID(int staffID);

    List<Project> getPreviousProjectsByStaffID(int staffID);

    String updateProject(Project project);

    String updateTask(ProjectTask task);
}
