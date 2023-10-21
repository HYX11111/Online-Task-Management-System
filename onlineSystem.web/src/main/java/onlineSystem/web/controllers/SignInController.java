package onlineSystem.web.controllers;

import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import onlineSystem.core.Administrator;
import onlineSystem.core.staff.Staff;
import onlineSystem.core.storingService.StaffAndProjectManagementService;

@Controller
@RequestMapping(value = {"/signin", "/"})
public class SignInController {
	
	@Autowired
	StaffAndProjectManagementService staffAndProjectManagementService;

	@GetMapping
	public String doGet() {
		return "signin";
	}

	@PostMapping
	public String doPost(HttpSession session,
						@RequestParam(value = "staffID", required = false) Integer staffID,
						@RequestParam (value = "password", required = false) String password) {
		
		if(staffID == null) {
			session.setAttribute("msg", "Invalid Staff ID.");
			return "signin";
		}
		
		if(staffID == Administrator.getStaffId() && password.equals(Administrator.getPasssword())) {
			session.setAttribute("Admin", new Administrator());
			return "redirect:/projectAndTask";
		}
		
		Staff staff = staffAndProjectManagementService.getStaffById(staffID);
		if(staff != null && password.equals(staff.getPassword())) {
			session.setAttribute("staff", staff);
			return "redirect:/userPage";
		} else {
			session.setAttribute("msg", "Invalid Staff ID or password.");
			return "signin";
		}
	}
}
