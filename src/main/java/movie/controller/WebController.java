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
	MovieRepository movieRepo;

	@GetMapping({ "/", "home" })
	public String homePage() {
		return "home";
	}

	@GetMapping("/memberView") // eventually add id to url once login is completed
	public String memberHomePage() {
		return "memberHome";
	}

	@GetMapping("/employeeView") // eventually add id to url once login is completed
	public String employeeHomePage() {
		return "employeeHome";
	}

	// Member registration

	@GetMapping("/memberRegistration")
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

	// viewAllMember
	@GetMapping("/viewAllMember")
	public String viewAllMembers(Model model) {
		if (memberRepo.findAll().isEmpty()) {
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

	// viewAllMovie
	@GetMapping({"/","/viewAllMovie"})
	public String viewAllMovies(Model model) {
		if (movieRepo.findAll().isEmpty()) {
			return addNewMovie(model);
		}
		model.addAttribute("movies", movieRepo.findAll());
		return "movielist";
	}

	// Adding movie

	@GetMapping("/addMovie")
	public String addNewMovie(Model model) {
		Movie m = new Movie();
		model.addAttribute("newMovie", m);
		return "movie";
	}

	@PostMapping("/addMovie")
	public String addNewMovie(@ModelAttribute Movie m, Model model) {
		movieRepo.save(m);
		return viewAllMovies(model);
	}

	// Edit delete update movie

	@GetMapping("/editMovie/{id}")
	public String showUpdateMovie(@PathVariable("id") long id, Model model) {
		Movie m = movieRepo.findById(id).orElse(null);
		model.addAttribute("newMovie", m);
		return "movie";
	}

	@PostMapping("/updateMovie/{id}")
	public String reviseMovie(Movie m, Model model) {
		movieRepo.save(m);
		return viewAllMovies(model);
	}

	@GetMapping("/deleteMovie/{id}")
	public String deleteMovieUser(@PathVariable("id") long id, Model model) {
		Movie m = movieRepo.findById(id).orElse(null);
		movieRepo.delete(m);
		return viewAllMovies(model);
	}

	@GetMapping("/rentalRegistration")
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
