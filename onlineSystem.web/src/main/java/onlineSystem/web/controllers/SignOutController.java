package onlineSystem.web.controllers;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequestMapping("/signout")
public class SignOutController {

	@GetMapping
	public String doGet(HttpSession session) {
		if (session.getAttribute("Admin") != null) {
	        session.removeAttribute("Admin"); 
	    }
		
		if(session.getAttribute("staff") != null) {
			session.removeAttribute("staff");
		}
		session.invalidate();
		return "redirect:/signin";
	}

}
