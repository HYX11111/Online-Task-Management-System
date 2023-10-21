package onlineSystem.web.controllers;

import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;

import onlineSystem.core.Administrator;
import onlineSystem.core.staff.Employee;
import onlineSystem.core.staff.GeneralManager;
import onlineSystem.core.staff.Manager;
import onlineSystem.core.staff.Staff;
import onlineSystem.core.storingService.StaffAndProjectManagementService;

@Controller
@RequestMapping("/registerStaff")
public class RegisterStaffController {

	@Autowired
		StaffAndProjectManagementService staffAndProjectManagementService;

	@GetMapping
	public String doGet(Model model, HttpSession session) {
		//Check whether the user has access	    
		Object admin = session.getAttribute("Admin");
		if(admin == null || !(admin instanceof Administrator)) {
			return "redirect:/signin";
		}
				
		Staff staff = new Staff();
		//Use the parent class as a DTO
		model.addAttribute("staff", staff);
		return "registerStaff";
	}
	
	@PostMapping
	public String doPost(@RequestParam("password") String password, 
						@RequestParam("repeatPassword") String repeatPassword,
						@ModelAttribute("Staff") Staff staff, HttpSession session) {
		
		Staff newStaff;
		if(!repeatPassword.equals(staff.getPassword())) {
			session.setAttribute("msg", "The passwords do not match.<br>");
			return "registerStaff"; 
		}

		if(staff.getPosition().equals("General Manager")) {
			newStaff = new GeneralManager();
		} else if (staff.getPosition().equals("Manager")) {
			newStaff = new Manager();
		} else {
			newStaff = new Employee();
		}
		
		newStaff.setStaffName(staff.getStaffName());
		newStaff.setPassword(staff.getPassword());
		newStaff.setDepartment(staff.getDepartment());
		
		String msg = staffAndProjectManagementService.saveStaff(newStaff).replace("\n","<br>");
		session.setAttribute("msg", "New Staff registered: <br>" + msg + "Please remember the Staff information.<br>");
		return "registerStaff";
	}
}
