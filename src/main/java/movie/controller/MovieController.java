package movie.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import movie.beans.Movie;
import movie.repository.MovieRepository;

public class MovieController {
	@Autowired
	MovieRepository movieRepo;
	
	// viewAllMovie
	@GetMapping("/viewAllMovie")
	public String viewAllMovies(Model model) {
		if(movieRepo.findAll().isEmpty()) {
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

}
