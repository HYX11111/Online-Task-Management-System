package onlineSystem.core.staff;

import javax.persistence.*;

import onlineSystem.core.RiskCodeAssessor;
import onlineSystem.core.enums.Department;
import onlineSystem.core.storingService.StaffAndProjectManagementService;

@Entity(name = "staff")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Staff {

	@Transient
    StaffAndProjectManagementService staffAndProjectManagementService;

	@Transient
    RiskCodeAssessor riskCodeAssessor;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer s_id;

    @Column
    String password; 

    @Column(name = "staff_name")
    String staffName;

    String position; 

    @Enumerated(EnumType.STRING)
    Department department;
    
    public Staff() {
        staffAndProjectManagementService = new StaffAndProjectManagementService();
        riskCodeAssessor = new RiskCodeAssessor();
    }

    //Method to display Tasks due, available to all Staff but with different implementations depending on the position of each Staff
    public String displayAlerts() {
		return null;
	}
    //Method to display all ongoing Projects(including information of related Tasks),available to all Staff but with different implementations depending on the position of each Staff
    public String displayOngingProjects() {
    	return null;
    }
    //Method to display all completed or aborted Projects(including information of related Tasks),available to all Staff but with different implementations depending on the position of each Staff
    public String displayPreviousProjects() {
    	return null;
    }

    public Integer getId() {
        return s_id;
    }

    public String getStaffName() {
        return staffName;
    }
    
    public String getPassword() { 
        return password; 
    }

    public String getPosition() {
        return position;
    }

    public Department getDepartment() {
        return department;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    @Override
    public String toString() {
        return "Staff ID: " + s_id + ", Staff Name: " + staffName + ", Position: " + position + ", Department: " + department + "\n";
    }
}

