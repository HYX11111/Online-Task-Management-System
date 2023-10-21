package onlineSystem.core;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import org.springframework.stereotype.Service;

import onlineSystem.core.enums.RiskCode;
import onlineSystem.core.projectAndTasks.ProjectTask;

//The class that assess the RiskCode for each Task, the RiskCode is determined bases on the project value and overdue time
@Service
public class RiskCodeAssessor {

    public RiskCode getRiskCode(ProjectTask task) {
    	if(task.isCompleted() == true) {
    		return RiskCode.GREEN; 
    	} else {
	        long delayDays = ChronoUnit.DAYS.between(task.getDueDate(), LocalDate.now());
	        double value = task.getValue();
	       
		    if(value >= 5000000) {
		        if(delayDays >= 30) return RiskCode.RED;
		        else if(delayDays >= 15) return RiskCode.ORANGE;
		        else if(delayDays >= 0) return RiskCode.YELLOW;
		        else return RiskCode.GREEN;
		    } else {
		        if(delayDays >= 60) return RiskCode.RED;
		        else if(delayDays >= 30) return RiskCode.ORANGE;
		        else if(delayDays >= 0) return RiskCode.YELLOW;
		        else return RiskCode.GREEN;
		    }
    	}
    }
}