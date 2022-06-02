package com.pyto.spark.restaurante.model;

import org.junit.Test;

import junit.framework.Assert;

public class MovieTest {

	private static final String SYNOPSIS = "test synopsis";
	private static final Long YEAR = Long.valueOf(2016);
	private static final String DIRECTOR = "test director";
	private static final String TITLE = "test title";
	private static final Long ID = Long.valueOf(1);

	@Test
	public void shouldReturnInstanceWhenCallConstructorWithNoParams() {
		Assert.assertNotNull(new Movie());
	}

	@Test
	public void shouldReturnInstanceWhenCallConstructorWithParams() {
		Assert.assertNotNull(new Movie(ID, TITLE, DIRECTOR, YEAR, SYNOPSIS));
	}
	
	@Test
	public void shouldBeAllAttributesAccesibles() {
		
		final Movie movie = createMovie(ID, TITLE, DIRECTOR, YEAR, SYNOPSIS);
		
		Assert.assertEquals(ID, movie.getId());
		Assert.assertEquals(TITLE, movie.getTitle());
		Assert.assertEquals(DIRECTOR, movie.getDirector());
		Assert.assertEquals(YEAR, movie.getYear());
		Assert.assertEquals(SYNOPSIS, movie.getSynopsis());
		
	}
	
	private Movie createMovie(final Long id, final String title, final String director, final Long year, final String synopsis) {
		
		final Movie movie = new Movie();
		
		movie.setId(id);
		movie.setTitle(title);
		movie.setDirector(director);
		movie.setYear(year);
		movie.setSynopsis(synopsis);
		
		return movie;
	}
	
}
