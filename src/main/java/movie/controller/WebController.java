package movie.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

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
import movie.beans.Employee;
import movie.repository.MemberRepository;
import movie.repository.MovieRepository;
import movie.repository.RentalRepository;
import movie.repository.EmployeeRepository;

@Controller
public class WebController {
	@Autowired
	MemberRepository memberRepo;
	@Autowired
	RentalRepository rentRepo;
	@Autowired
	MovieRepository movieRepo;
	@Autowired
	EmployeeRepository empRepo;	

	@GetMapping({ "/", "home" })
	public String homePage() {
		return "home";
	}
	
	@GetMapping("/employeeRegistration")
	public String addNewEmployee(Model model) {
		Employee e = new Employee();
		model.addAttribute("newEmployee", e);
		return "employeeRegistration";
	}

	@PostMapping("/employeeRegistration")
	public String addNewEmployee(@ModelAttribute Employee e, Model model) {
		System.out.println(e.toString() );
		empRepo.save(e);
		return "home";
	}
	
	// Member registration

	@GetMapping("/memberRegistration")
	public String addNewMembers(Model model) {
		Member m = new Member();
		model.addAttribute("newMember", m);
		return "registration";
	}

	@PostMapping("/memberRegistration")
	public String addNewMembers(@ModelAttribute Member m, Model model, HttpSession session) {
		memberRepo.save(m);
		long memID = m.getMemberID();
		session.setAttribute("memID", memID);
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
	@GetMapping("/viewAllMovie")
	public String viewAllMovies(Model model) {
		if (movieRepo.findAll().isEmpty()) {
			return addNewMovie(model);
		}
		model.addAttribute("movies", movieRepo.findAll());
		return "movielist";
	}

	@GetMapping("/viewAllMovies")
	public String viewAllMovie(Model model) {
		if (movieRepo.findAll().isEmpty()) {
			return addNewMovie(model);
		}
		model.addAttribute("movies", movieRepo.findAll());
		return "membersMovieList";
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

//================================================================
// RENTAL
//================================================================
	
	@GetMapping("/rentalRegistration")
	public String addNewRental(Model model, HttpSession session) {
		Rental r = new Rental();
		Member mem = (Member) session.getAttribute("member");
		r.setMember(mem);
		model.addAttribute("newRental", r);
		
		List<Movie> movies = movieRepo.findAll();
		model.addAttribute("movies", movies);
		return "rental";
	}

	@PostMapping("/rentalRegistration")
	public String addNewRental(@ModelAttribute Rental r, Model model, HttpSession session) {
		
		try {
		rentRepo.save(r);
		
		}catch(Exception e) {
			model.addAttribute("rentalFail", "You must be a member to rent a movie!");
			Rental redo = new Rental();
			model.addAttribute("newRental", redo);
			
			List<Movie> movies = movieRepo.findAll();
			model.addAttribute("movies", movies);
			return "rental";
		}
		model.addAttribute("rental", r);
		return memberRentals(model, session);
		
	}
	
	// viewAllRental
		@GetMapping("/memberRentals")
		public String memberRentals(Model model, HttpSession session) {
//			if (rentRepo.findAll().isEmpty()) {
//				return addNewRental(model);
//			}
			Member m = (Member)session.getAttribute("member");
			long memID = m.getMemberID();
			model.addAttribute("rentals", rentRepo.findMemberRentals(memID));
			return "memberRentals";
		}
		
		
		@GetMapping("/checkIn/{id}")
		public String checkInMovie(@PathVariable("id") long id, Model model, HttpSession session) {
			Rental r = rentRepo.findById(id).orElse(null);
			model.addAttribute("newRental", r);
			return memberRentals(model, session);
		}
	
		@GetMapping("/update/{id}")
		public String updateRental(@PathVariable("id") long id, Model model, HttpSession session) {
		Rental r = rentRepo.findById(id).orElse(null);
		r.setCheckinDateTime(LocalDateTime.now());
		rentRepo.save(r);
		return memberRentals(model, session);
		}
// END RENTAL
	
	@GetMapping( "/memberLogin")
	public String memberLogin(Model model) {
		Member m = new Member();
		model.addAttribute("member", m);
		return "memberLogin";
	}

	@PostMapping("/memberLogin")
	public String memberLogin(@ModelAttribute Member m, Model model, HttpSession session) {
		List<Member> NameMatches = memberRepo.findByFirstNameAndLastName(m.getFirstName(), m.getLastName());
		System.out.println("NameMathces lenght: "+ NameMatches.size());
		for(Member mem : NameMatches) {
			if(mem.getCreditCard() == m.getCreditCard()) {//if username (first and last name) matches password (credit card)
				
				session.setAttribute("member", mem);
				System.out.println("Session Attribute Set to: " + session.getAttribute("member"));

				return "memberHome";
			}
		}
		model.addAttribute("failMessage", "Incorrect Login");
		return "memberLogin";
	}

	@GetMapping( "/employeeLogin")
	public String employeeLogin(Model model) {
		Employee e = new Employee();
		model.addAttribute("employee", e);
		return "employeeLogin";
	}

	@PostMapping("/employeeLogin")
	public String employeeLogin(@ModelAttribute Employee e, Model model) {
		List<Employee> userNameMatches = empRepo.findByUserName(e.getUserName());
		System.out.println("usernames matches lenght: "+ userNameMatches.size());
		for(Employee emp : userNameMatches) {
			System.out.println("emp: " + emp.toString() + ", e: " + e.toString());
			if(emp.getPassword().equalsIgnoreCase(e.getPassword())) {//if username matches password 			
				return "employeeHome";
			}
		}
		model.addAttribute("failMessage", "Incorrect Employee Credentials");
		return "employeeLogin";
	}

	@GetMapping( "/memberView")
	public String memberHomePage() {
		return "memberHome";
	}

	@GetMapping( "/employeeView")
	public String employeeHomePage() {
		return "employeeHome";
	}

}
