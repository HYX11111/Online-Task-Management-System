package onlineSystem.core.projectAndTasks;

import java.time.LocalDate;
import javax.persistence.*;

@Entity(name = "project_task")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class ProjectTask {
    
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer t_id;

    @ManyToOne
    @JoinColumn(name = "project_id")
    Project project;
    
    @Column(name = "type")
    String type;

    @Column(name = "task_name")
    String taskName;

    @Column(name = "value")
    double value;

    @Column(name = "due_date")
    LocalDate dueDate;

    @Column(name = "is_completed")
    boolean isCompleted;

    @Column(name = "is_aborted")
    boolean isAborted;

    public void markAsAborted() {
        this.isAborted = true;
    }

    public void markAsCompleted() {
        this.isCompleted = true;
    }

    public ProjectTask() {
        isCompleted = false;
        isAborted = false;
    }

    

	public LocalDate getDueDate() {
        return dueDate;
    }    
    
    public double getValue() {
        return value;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

     public boolean isAborted() {
        return isAborted;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public void setTaskType(String taskType) {
        this.type = taskType;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    @Override
    public String toString() { 
        return  "Project ID: " + project.p_id + "Project Name: " + project.projectName + "\nTask ID: " + t_id + "\nType of Task: " + type + "\nName of Task: " + taskName + "\nValue: " + value + "\nDue Date: " + dueDate + "\nStatus - Is Completed: " + isCompleted + "\nStatus - Is Aborted: " + isAborted + "\n";
    }
	
    public String getTaskName() {
		return taskName;
	}
}