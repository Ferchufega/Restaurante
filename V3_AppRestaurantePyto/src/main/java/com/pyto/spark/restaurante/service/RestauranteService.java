package com.pyto.spark.restaurante.service;

import java.util.List;

import com.pyto.spark.restaurante.model.Movie;

public interface RestauranteService {

	Movie addMovie(final Movie movie);

	void deleteMovie(final Long id);

	Movie updateMovie(final Movie movie);

	Movie getMovie(final Long id);

	List<Movie> getMovies();
	
}
