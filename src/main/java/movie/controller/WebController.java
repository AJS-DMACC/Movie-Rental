package movie.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import movie.beans.Employee;
import movie.beans.Member;
import movie.beans.Rental;
import movie.repository.EmployeeRepository;
import movie.repository.MemberRepository;
import movie.repository.RentalRepository;

@Controller
public class WebController {
	@Autowired
	MemberRepository repo;
	RentalRepository rentRepo;
	@Autowired
	EmployeeRepository empRepo;
	
	@GetMapping("/viewAll")
	public String viewAllMembers(Model model) {
		if(repo.findAll().isEmpty()) {
			return addNewMember(model);
		}
		model.addAttribute("members", repo.findAll());
		return "results";
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
	
	@GetMapping("/memberRegistration")
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
	
	@GetMapping({ "/", "home" })
	public String homePage() {
	return "home";
	}

	@GetMapping( "/memberLogin")
	public String memberLogin(Model model) {
		Member m = new Member();
		model.addAttribute("member", m);
		return "memberLogin";
	}
	
	@PostMapping("/memberLogin")
	public String memberLogin(@ModelAttribute Member m, Model model) {
		List<Member> NameMatches = repo.findByFirstNameAndLastName(m.getFirstName(), m.getLastName());
		System.out.println("NameMathces lenght: "+ NameMatches.size());
		for(Member mem : NameMatches) {
			if(mem.getCreditCard() == m.getCreditCard()) {//if username (first and last name) matches password (credit card)
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
	
	@GetMapping("/edit/{id}")
	public String showUpdateMember(@PathVariable("id") long id, Model model) {
		Member m = repo.findById(id).orElse(null);
		System.out.println("ITEM TO EDIT: " + m.toString());
		model.addAttribute("newMember", m);
		return "registration";
	}

	@PostMapping("/update/{id}")
	public String reviseMember(Member m, Model model) {
		repo.save(m);
		return viewAllMembers(model);
	}
	
	@GetMapping("/delete/{id}")
	public String deleteMember(@PathVariable("id") long id, Model model) {
		Member m = repo.findById(id).orElse(null);
	    repo.delete(m);
	    return viewAllMembers(model);
	}
	
}
