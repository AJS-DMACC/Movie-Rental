package movie.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import movie.beans.Member;
import movie.repository.MemberRepository;

@Controller
public class WebController {
	@Autowired
	MemberRepository repo;
	
	@GetMapping({"/", "/memberRegistration"})
	public String addNewMember(Model model) {
		Member m = new Member();
		model.addAttribute("newMember", m);
	return "registration";
	}
	
	@PostMapping("/memberRegistration")
	public String addNewMember(@ModelAttribute Member m, Model model) {
		repo.save(m);
		return "home";
	}
	
	@GetMapping("/homePage")
	public String memberHomePage() {
	return "home";
	}
}
