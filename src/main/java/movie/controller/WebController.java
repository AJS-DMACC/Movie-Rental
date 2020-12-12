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
import movie.beans.MovieType;
import movie.beans.Payment;
import movie.beans.Rental;
import movie.beans.Employee;
import movie.repository.MemberRepository;
import movie.repository.MovieRepository;
import movie.repository.MovieTypeRepository;
import movie.repository.PaymentRepository;
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
	@Autowired
	PaymentRepository payRepo;
	@Autowired
	MovieTypeRepository movieTypeRepo;

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
	public String addNewMembers(@ModelAttribute Member m, Model model, HttpSession session) {
		System.out.println(m);
		memberRepo.save(m);
		session.setAttribute("member", m);
		System.out.println(m);
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
	public String reviseMember(Member m, Model model, HttpSession session) {
		memberRepo.save(m);
		session.setAttribute("member", m);
		System.out.println("Session Attribute Set to: " + session.getAttribute("member"));

		return "memberHome";
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

		List<MovieType> movieTypes = movieTypeRepo.findAll();
		model.addAttribute("movieTypes", movieTypes);
		
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
		
		List<MovieType> movieTypes = movieTypeRepo.findAll();
		model.addAttribute("movieTypes", movieTypes);
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
		System.out.println(mem);
		model.addAttribute("newRental", r);
		
		List<Movie> movies = movieRepo.findAll();
		model.addAttribute("movies", movies);
		return "rental";
	}

	@PostMapping("/rentalRegistration")
	public String addNewRental(@ModelAttribute Rental r, Model model, HttpSession session) {
		
		try {
		rentRepo.save(r);
		System.out.println(r.toString());
		
		}catch(Exception e) {
			model.addAttribute("rentalFail", "Something went wrong, please contact us to resolve this issue!");
//			Rental redo = new Rental();
//			model.addAttribute("newRental", redo);
//			
//			List<Movie> movies = movieRepo.findAll();
//			model.addAttribute("movies", movies);
//			return "rental";
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

// END RENTAL =============================================================
	
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
	
	//================================================================
	// Payment Type
	//================================================================
	
	@GetMapping("/viewAllPaymentTypes")
	public String viewAllPaymentTypes(Model model) {
		if (payRepo.findAll().isEmpty()) {
			return addNewPaymentType(model);
		}
		model.addAttribute("paymentTypes", payRepo.findAll());
		return "paymentTypeList";
	}


	@GetMapping("/addPaymentType")
	public String addNewPaymentType(Model model) {
		Payment p = new Payment();
		model.addAttribute("newPaymentType", p);
		return "addPaymentType";
	}

	@PostMapping("/addPaymentType")
	public String addNewPaymentType(@ModelAttribute Payment p, Model model) {
		payRepo.save(p);
		return viewAllPaymentTypes(model);
	}

	@GetMapping("/editPaymentType/{id}")
	public String showUpdatePaymentType(@PathVariable("id") long id, Model model) {
		Payment p = payRepo.findById(id).orElse(null);
		model.addAttribute("newPaymentType", p);
		return "addPaymentType";
	}

	@PostMapping("/updatePaymentType/{id}")
	public String revisePaymentType(Payment p, Model model) {
		payRepo.save(p);
		return viewAllPaymentTypes(model);
	}

	@GetMapping("/deletePaymentType/{id}")
	public String deletePaymentType(@PathVariable("id") long id, Model model) {
		Payment p = payRepo.findById(id).orElse(null);
		payRepo.delete(p);
		return viewAllPaymentTypes(model);
	}

//================================================================
// Movie Type
//================================================================
		
		@GetMapping("/viewAllMovieTypes")
		public String viewAllMovieTypes(Model model) {
			if (movieTypeRepo.findAll().isEmpty()) {
				return addNewMovieType(model);
			}
			model.addAttribute("moiveTypes", movieTypeRepo.findAll());
			return viewAllMovie(model);
		}


		@GetMapping("/addMovieType")
		public String addNewMovieType(Model model) {
			MovieType mt = new MovieType();
			model.addAttribute("newMovieType", mt);
			return "MovieType";
		}

		@PostMapping("/addMovieType")
		public String addNewMovieType(@ModelAttribute MovieType mt, Model model) {
			movieTypeRepo.save(mt);
			return addNewMovie(model);
		}

		@GetMapping("/editMovieType/{id}")
		public String showUpdateMovieType(@PathVariable("id") long id, Model model) {
			MovieType mt = movieTypeRepo.findById(id).orElse(null);
			model.addAttribute("newMovieType", mt);
			return "employeeHome";
		}

		@PostMapping("/updateMovieType/{id}")
		public String reviseMovieType(MovieType mt, Model model) {
			movieTypeRepo.save(mt);
			return viewAllMovieTypes(model);
		}

		@GetMapping("/deleteMovieType/{id}")
		public String deleteMovieType(@PathVariable("id") long id, Model model) {
			MovieType mt = movieTypeRepo.findById(id).orElse(null);
			movieTypeRepo.delete(mt);
			return viewAllMovieTypes(model);
		}

// End MovieType =============================================================
		
	@GetMapping("/style.css")
	public String stylesheet() {
		return "style.css";
	}
}
