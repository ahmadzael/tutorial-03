package com.apap.tu03.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.apap.tu03.model.MovieModel;
import com.apap.tu03.service.MovieService;

@Controller
public class MovieController {

	public MovieController() {
		
	}
	
	@Autowired
	private MovieService movieService;
	
	@RequestMapping("/movie/add")
	public String add(@RequestParam(value = "id",required = true)String id,
			@RequestParam(value = "title",required = true) String title,
			@RequestParam(value = "genre",required = true) String genre,
			@RequestParam(value = "budget",required = true) Long budget,
			@RequestParam(value = "duration",required = true) Integer duration) 
	{
		MovieModel movie = new MovieModel(id,title,genre,budget,duration);
		movieService.addMovie(movie);
		
		return "add";
	}
	
	/*
	@RequestMapping("/movie/view")
	public String view(@RequestParam("id") String id, Model model) {
		MovieModel archive = movieService.getMovieDetail(id);
		model.addAttribute("movie", archive);
		return "view-movie";
	}
	*/
	
	@RequestMapping("/movie/viewall")
	public String viewAll(Model model) {
		List<MovieModel> archive = movieService.getMovieList();
		model.addAttribute("movies",archive);
		return "viewall-movie";
	}
	
	@RequestMapping(value= {"/movie/view", "/movie/view/{id}"}  )
	public String viewByID(@PathVariable Optional<String> id,Model model) {
		String result = "errorPage";
		if (id.isPresent()) {			
			MovieModel archive = movieService.getMovieDetail(id.get());
			if (archive != null) {
				model.addAttribute("movie", archive);
				result = "view-movie";
			}
		} 
		return result;
	} 
	
	@RequestMapping("/movie/update/{id}/duration/{duration}")
	public String updateMovie(@PathVariable String id, 
			@PathVariable int duration,Model model){
		String result = "errorPage";
		MovieModel archive = movieService.getMovieDetail(id);
		if (archive != null) {
			
			archive.setDuration(duration);
			
			model.addAttribute("movie", archive);
			result = "update-movie";
		}
		

		return result;
	}
	
	@RequestMapping(value= {"/movie/delete", "/movie/delete/{id}"}  )
	public String deleteMovie(@PathVariable Optional<String> id, Model model) {
		String result = "errorPage";
		if (id.isPresent()) {
			MovieModel archive = movieService.getMovieDetail(id.get());
			if (archive != null) {
				
				List<MovieModel> listMovie = movieService.getMovieList();

				listMovie.remove(archive);
				
				model.addAttribute("id", id.get());
				
				result = "delete-movie";
			}
		}		
		return result;
	}
}
