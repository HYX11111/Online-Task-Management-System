package onlineSystem.core.projectAndTasks;

import java.time.LocalDate;
import javax.persistence.Entity;

@Entity
public class PaymentTask extends ProjectTask {
	
	public PaymentTask() {
		this.type = "Payment Task";
	}

    public PaymentTask(Project project, String taskName, double value, LocalDate dueDate) {
        this.type = "Payment Task";
        this.project = project;
        this.taskName = taskName;
        this.value = value;
        this.dueDate = dueDate;
    }
}