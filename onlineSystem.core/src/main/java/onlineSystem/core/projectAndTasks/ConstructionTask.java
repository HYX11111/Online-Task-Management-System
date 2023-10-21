package onlineSystem.core.projectAndTasks;

import java.time.LocalDate;
import javax.persistence.Entity;

@Entity
public class ConstructionTask extends ProjectTask {
	
	public ConstructionTask() {
		this.type = "Construction Project Task";
	}

	public ConstructionTask(Project project, String taskName, double value, LocalDate dueDate) {
        this.type = "Construction Project Task";
        this.project = project;
        this.taskName = taskName;
        this.value = value;
        this.dueDate = dueDate;
    }
}