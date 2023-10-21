package onlineSystem.core.projectAndTasks;

import java.util.List;
import javax.persistence.*;

import onlineSystem.core.enums.Department;
import onlineSystem.core.staff.Staff;

@Entity(name = "project")
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer p_id;

    @Column(name = "project_name")
    String projectName;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    List<ProjectTask> tasks;

    @Enumerated(EnumType.STRING)
    Department department;

    @ManyToOne
    @JoinColumn(name = "employee_id") 
    Staff staff; 

    @Column(name = "is_completed")
    boolean isCompleted;

    @Column(name = "is_aborted")
    boolean isAborted;
    
    
    public Project() {
        isAborted = false;
        isCompleted = false;
    }

    public void markAsAborted() {
        isAborted = true;
        //If a Project is aborted, all of its Tasks will also be marked as aborted 
        for(ProjectTask task : tasks) { 
            task.markAsAborted();
        }
    }

    public void markAsCompleted() {
        isCompleted = true;
    }

    public String getProjectName() {
		return projectName;
	}

	public Staff getStaff() {
        return staff;
    }

    public Department getDepartment() {
        return department;
    }

    public List<ProjectTask> getProjectTasks() {
        return tasks;
    }
    
    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public void setTasks(List<ProjectTask> tasks) {
        this.tasks = tasks;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }


    public void setStaff(Staff staff) {
        this.staff = staff;
    }

    @Override
    public String toString() { 
        StringBuilder sb = new StringBuilder();
        sb.append("Project ID: " + p_id + " Project Name: " + projectName + "\nDepartment: " + department + "\nResponsible staff ID: " + staff.getId() + "\nStatus - is completed?: " + isCompleted + " \nStatus - is aborted?: " + isAborted + "\nTasks:\n");
        for(ProjectTask task: tasks) {
            sb.append(task.toString()); 
        }
        return sb.append("\n").toString();
    }
}
