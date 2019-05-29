/**
 * CS 401 Assignment 3 - Movie DB
 * 
 * @author: Andrew Frantsuzov
 * @author: Daniyal Adzhiyev
 * @version 1.0 05/28/2019
 * 
 * This Application allows a user to query a Movie Database based on field criteria
 * such as rating, actors, duration etc. using a composite software pattern to create the queries.  When a user
 * selects a field to query by, a Red Black Tree will be created where the Keys will be the field criteria,
 * and the Values will be a HashSet of the movie ID's with the same field criteria.  The Red Black Trees will 
 * be stored in a HashMap to allow queries based on multiple fields.  
 */
package moviesdb;

public class Movie {

	private int id;
	private String color;
	private String movie_title;
	private String genres;
	private Integer duration;
	private String director_name;
	private String actor_1_name;
	private String actor_2_name;
	private String actor_3_name;
	private String plot_keywords;
	private String movie_imdb_link;
	private String language;
	private String country;
	private String content_rating;
	private Integer title_year;
	private Double imdb_score;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		color = color;
	}

	public String getMovie_title() {
		return movie_title;
	}

	public void setMovie_title(String movie_title) {
		this.movie_title = movie_title;
	}

	public String getGenres() {
		return genres;
	}

	public void setGenres(String genres) {
		this.genres = genres;
	}

	public Integer getDuration() {
		return duration;
	}

	public void setDuration(Integer duration) {
		this.duration = duration;
	}

	public String getDirector_name() {
		return director_name;
	}

	public void setDirector_name(String director_name) {
		this.director_name = director_name;
	}

	public String getActor_1_name() {
		return actor_1_name;
	}

	public void setActor_1_name(String actor_1_name) {
		this.actor_1_name = actor_1_name;
	}

	public String getActor_2_name() {
		return actor_2_name;
	}

	public void setActor_2_name(String actor_2_name) {
		this.actor_2_name = actor_2_name;
	}

	public String getActor_3_name() {
		return actor_3_name;
	}

	public void setActor_3_name(String actor_3_name) {
		this.actor_3_name = actor_3_name;
	}

	public String getPlot_keywords() {
		return plot_keywords;
	}

	public void setPlot_keywords(String plot_keywords) {
		this.plot_keywords = plot_keywords;
	}

	public String getMovie_imdb_link() {
		return movie_imdb_link;
	}

	public void setMovie_imdb_link(String movie_imdb_link) {
		this.movie_imdb_link = movie_imdb_link;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getContent_rating() {
		return content_rating;
	}

	public void setContent_rating(String content_rating) {
		this.content_rating = content_rating;
	}

	public Integer getTitle_year() {
		return title_year;
	}

	public void setTitle_year(Integer title_year) {
		this.title_year = title_year;
	}

	public Double getImdb_score() {
		return imdb_score;
	}

	public void setImdb_score(Double imdb_score) {
		this.imdb_score = imdb_score;
	}
	
	@Override
	public  String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Title: ");
		sb.append(this.getMovie_title());
		sb.append(", ");
		sb.append("Year: ");
		sb.append(this.getTitle_year());
		sb.append(", ");
		sb.append("Score: ");
		sb.append(this.getImdb_score());
		sb.append(", ");
		sb.append("Rating: ");
		sb.append(this.getContent_rating());
		sb.append(", ");
		return sb.toString();
	}
}

