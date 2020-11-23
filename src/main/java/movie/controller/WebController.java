package movie.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import movie.beans.Member;
import movie.beans.Movie;
import movie.beans.Rental;
import movie.repository.MemberRepository;
import movie.repository.MovieRepository;
import movie.repository.RentalRepository;

@Controller
public class WebController {
	@Autowired
	MemberRepository memberRepo;
	RentalRepository rentRepo;
	
	// Member registration
	
	@GetMapping({"/", "/memberRegistration"})
	public String addNewMembers(Model model) {
		Member m = new Member();
	model.addAttribute("newMember", m);
	return "registration";
	}
	
	@PostMapping("/memberRegistration")
	public String addNewMembers(@ModelAttribute Member m, Model model) {
		memberRepo.save(m);
	return viewAllMembers(model);
	}
	
	//viewAllMember
	@GetMapping("/viewAllMember")
	public String viewAllMembers(Model model) {
	if(memberRepo.findAll().isEmpty()) {
	return addNewMembers(model);
	}
	model.addAttribute("members", memberRepo.findAll());
	return "results";
	}
	
	// Edit delete update member
	
	@GetMapping("/editMember/{id}")
	public String showUpdateMember(@PathVariable("id") long id, Model model) {
		Member m = memberRepo.findById(id).orElse(null);
	model.addAttribute("newMember", m);
	return "registration";
	}

	@PostMapping("/updateMember/{id}")
	public String reviseMember(Member m, Model model) {
		memberRepo.save(m);
	return viewAllMembers(model);
	}
	
	@GetMapping("/deleteMember/{id}")
	public String deleteMemberUser(@PathVariable("id") long id, Model model) {
		Member m = memberRepo.findById(id).orElse(null);
		memberRepo.delete(m);
	return viewAllMembers(model);
	}

	@GetMapping( "/rentalRegistration")
	public String addNewRental(Model model) {
		Rental r = new Rental();
		model.addAttribute("newRental", r);
		return "addRental";
	}
	
	@PostMapping("/rentalRegistration")
	public String addNewRental(@ModelAttribute Rental r, Model model) {
		rentRepo.save(r);
		model.addAttribute("rental", r);
		return "confirmRental";
	}
	
}
