package com.pyto.spark.restaurante.model;

public class Movie {

	private Long id;
	private String title;
	private String director;
	private Long year;
	private String synopsis;
	
	public Movie() {
		super();
	}

	public Movie(final Long id, final String title, final String director, final Long year, final String synopsis) {
		super();
		this.id = id;
		this.title = title;
		this.director = director;
		this.year = year;
		this.synopsis = synopsis;
	}

	public Long getId() {
		return id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	public String getDirector() {
		return director;
	}

	public void setDirector(final String director) {
		this.director = director;
	}

	public Long getYear() {
		return year;
	}

	public void setYear(final Long year) {
		this.year = year;
	}

	public String getSynopsis() {
		return synopsis;
	}

	public void setSynopsis(final String synopsis) {
		this.synopsis = synopsis;
	}	
		
}
