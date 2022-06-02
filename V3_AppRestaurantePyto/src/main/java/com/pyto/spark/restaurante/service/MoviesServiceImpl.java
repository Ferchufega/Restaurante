package com.pyto.spark.restaurante.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pyto.spark.restaurante.dao.MoviesRespository;
import com.pyto.spark.restaurante.model.Movie;

@Service
public class MoviesServiceImpl implements RestauranteService {

	@Autowired
	private MoviesRespository moviesRepository;
	
	public Movie addMovie(Movie movie) {
		return moviesRepository.addMovie(movie);
	}

	public void deleteMovie(Long id) {
		moviesRepository.deleteMovie(id);
	}

	public Movie updateMovie(Movie movie) {
		return moviesRepository.updateMovie(movie);
	}

	public Movie getMovie(Long id) {
		return moviesRepository.getMovie(id);
	}

	public List<Movie> getMovies() {
		return moviesRepository.getMovies();
	}

}
