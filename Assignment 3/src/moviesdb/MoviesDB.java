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

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/*
 * This class creates a movie Data Base
 */
public class MoviesDB<T extends Comparable<T>> {

	private String fileName; // file to be read from

	private Map<String, RedBlackTree<T, HashSet<Integer>>> indexTreeMap = new HashMap<String, RedBlackTree<T, HashSet<Integer>>>(); // HashMap
																																	// of
																																	// RedBlackTrees

	private Movie[] db; // movie db
	private int n = 0; // counts how many movies in file

	/*
	 * Constructor
	 */
	public MoviesDB(String fileName) throws IOException {
		this.fileName = fileName; // sets fileName

		createDB(); // generates the data base of movies
	}

	/*
	 * Creates the Red Black Tree based on field criteria
	 */
	public void addFieldIndex(String field) {
		// RBT to be inserted into map based on field
		RedBlackTree<T, HashSet<Integer>> rbt = new RedBlackTree<T, HashSet<Integer>>();

		HashSet<Integer> set = new HashSet<Integer>(); // set to insert movie ID's as the RBT's values

		if (field == "movie_title") {
			addFieldTitle(field, rbt, set);
		}
		
		// calls addField method based on field input
		if (field == "year") {
			addFieldYear(field, rbt, set);
		}

		else if (field == "score") {
			addFieldScore(field, rbt, set);
		}

		else if (field == "color") {
			addFieldColor(field, rbt, set);
		}

		else if (field == "director") {
			addFieldDirector(field, rbt, set);
		}

		else if (field == "act_1") {
			addFieldActor1(field, rbt, set);
		}

		else if (field == "act_2") {
			addFieldActor2(field, rbt, set);
			
		} else if (field == "act_3") {
			addFieldActor3(field, rbt, set);
		}

		else if (field == "language") {
			addFieldLanguage(field, rbt, set);
		}

		else if (field == "country") {
			addFieldCountry(field, rbt, set);
		}

		else if (field == "content_rating") {
			addFieldContentRating(field, rbt, set);
		}
		indexTreeMap.put(field, rbt); //puts the generated RBT into the HashMap
	}

	/*
	 * Prints movie info
	 */
	public void print(int index) {
		System.out.println("---------------------------------");
		System.out.println("id: " + db[index - 1].getId());
		System.out.println("color: " + db[index - 1].getColor());
		System.out.println("title: " + db[index - 1].getMovie_title());
		System.out.println("duration: " + db[index - 1].getDuration());
		System.out.println("director name: " + db[index - 1].getDirector_name());
		System.out.println("actor 1: " + db[index - 1].getActor_1_name());
		System.out.println("actor 2: " + db[index - 1].getActor_2_name());
		System.out.println("actor 3: " + db[index - 1].getActor_3_name());
		System.out.println("movie imdb link: " + db[index - 1].getMovie_imdb_link());
		System.out.println("language: " + db[index - 1].getLanguage());
		System.out.println("country: " + db[index - 1].getCountry());
		System.out.println("content rating: " + db[index - 1].getContent_rating());
		System.out.println("title year: " + db[index - 1].getTitle_year());
		System.out.println("imdb score: " + db[index - 1].getImdb_score());
		System.out.println("---------------------------------");
	}

	/*
	 * Gets the HashMap of all the RedBlackTree's
	 */
	public Map<String, RedBlackTree<T, HashSet<Integer>>> getIndexTreeMap() {
		return indexTreeMap;
	}

	/*
	 * Generates the movie DB
	 */
	private void createDB() throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(fileName));
		int i = 0; //index of movie db
		String str; //String to read each movie line
		String[] movieAttributes; //Array of movie attributes for each mov
		Movie mov; //movie to be inserted into the DB

		// gets the number of lines in file
		while ((str = br.readLine()) != null) {
			n++;
		}
		br.close();
		n = n - 1; // subtracts 1 for first line of file, giving the number of movies
		br = new BufferedReader(new FileReader(fileName));
		db = new Movie[n]; //sets the size of the db to the number of movies
		br.readLine();
		while ((str = br.readLine()) != null && i < n) {
			mov = new Movie(); //creates new movie object
			
			//Splits the movie string by commas for each attribute
			movieAttributes = str.split(",");
			if (movieAttributes.length > 14) { // handle the case where you get comma's in tittles like "I, Robot"
				movieAttributes = fixUpCommasInQuotes(str);
			}
			mov.setId(Integer.parseInt(movieAttributes[0])); //sets the movie id
			mov.setColor(movieAttributes[1]);
			mov.setMovie_title(movieAttributes[2].trim());
			
			//Sets the duration to an Integer value if possible
			try {
				mov.setDuration(Integer.parseInt(movieAttributes[3]));
			} catch (Exception e) {
				mov.setDuration(-1);
			}
			mov.setDirector_name(movieAttributes[4]);
			mov.setActor_1_name(movieAttributes[5]);
			mov.setActor_2_name(movieAttributes[6]);
			mov.setActor_3_name(movieAttributes[7]);
			mov.setMovie_imdb_link(movieAttributes[8]);
			mov.setLanguage(movieAttributes[9]);
			mov.setCountry(movieAttributes[10]);
			mov.setContent_rating(movieAttributes[11]);
			//Sets the title year to an Integer value if possible
			try {
				mov.setTitle_year(Integer.parseInt(movieAttributes[12]));
			} catch (Exception e) {
				mov.setTitle_year(-1);
			}

			mov.setImdb_score(Double.parseDouble((movieAttributes[13]))); //sets the movie IMDB score to a Double
			db[i] = mov; //adds the movie object to the db
			i++; //increments db index
		}
		br.close();

	}

	/*
	 * This method handles the case for when there are comma's in the movie title so that the movie
	 * attributes remain accurate
	 */
	private static String[] fixUpCommasInQuotes(String str) {

		String[] movieAttributes = str.split(",");
		ArrayList<String> merge = new ArrayList<String>();
		merge.addAll(Arrays.asList(movieAttributes));

		if (movieAttributes.length > 14) // this is to handle cases where there is a comma in quotes ""
		{
			String[] movieQuotes = str.split("\"");
			int items_prior = 0;

			for (int c = 1; c < movieQuotes.length; c += 2) {
				items_prior += movieQuotes[c - 1].split(",").length;
				int items_to_merge = movieQuotes[c].split(",").length;

				StringBuilder mergedstr = new StringBuilder();

				for (int m = 0; m < items_to_merge; m++) {
					mergedstr.append(merge.get(items_prior));
					merge.remove(items_prior);
				}

				merge.add(items_prior, mergedstr.toString());
			}
		}

		return merge.toArray(movieAttributes); //returns array
	}

	/*
	 * This method takes the string field, RBT, and a HashSet to insert a Red Black Tree into the HashMap
	 * where the keys will be the different years, and the Values will be a set of movie IDs with that year.
	 */
	private void addFieldYear(String field, RedBlackTree<T, HashSet<Integer>> rbt, HashSet<Integer> set) {
		for (int i = 0; i < n; i++) {
			set = new HashSet<Integer>();

			if (!rbt.contains((T) db[i].getTitle_year())) {
				set.add(db[i].getId());
				rbt.put((T) db[i].getTitle_year(), set);
			}

			else {
				HashSet<Integer> insert = rbt.get((T) db[i].getTitle_year());
				insert.add(db[i].getId());
				rbt.put((T) db[i].getTitle_year(), insert);
			}
		}

	}
	/*
	 * This method takes the string field, RBT, and a HashSet to insert a Red Black Tree into the HashMap
	 * where the keys will be the different color options, and the Values will be a set of movie IDs with that 
	 * color optiom.
	 */
	private void addFieldColor(String field, RedBlackTree<T, HashSet<Integer>> rbt, HashSet<Integer> set) {

		for (int i = 0; i < n; i++) {
			set = new HashSet<Integer>();

			if (!rbt.contains((T) db[i].getColor())) {
				set.add(db[i].getId());
				rbt.put((T) db[i].getColor(), set);
			}

			else {
				HashSet<Integer> insert = rbt.get((T) db[i].getColor());
				insert.add(db[i].getId());
				rbt.put((T) db[i].getColor(), insert);
			}
		}

	}
	/*
	 * This method takes the string field, RBT, and a HashSet to insert a Red Black Tree into the HashMap
	 * where the keys will be the different imdb scores, and the Values will be a set of movie IDs with that 
	 * imdb score.
	 */
	private void addFieldScore(String field, RedBlackTree<T, HashSet<Integer>> rbt, HashSet<Integer> set) {
		for (int i = 0; i < n; i++) {
			set = new HashSet<Integer>();

			if (!rbt.contains((T) db[i].getImdb_score())) {
				set.add(db[i].getId());
				rbt.put((T) db[i].getImdb_score(), set);

			}

			else {
				HashSet<Integer> insert = rbt.get((T) db[i].getImdb_score());
				insert.add(db[i].getId());
				rbt.put((T) db[i].getImdb_score(), insert);
			}
		}
	}
	/*
	 * This method takes the string field, RBT, and a HashSet to insert a Red Black Tree into the HashMap
	 * where the keys will be the different movie titles, and the Values will be a set of movie IDs with 
	 * the same movie title.
	 */
	private void addFieldTitle(String field, RedBlackTree<T, HashSet<Integer>> rbt, HashSet<Integer> set) {
		for (int i = 0; i < n; i++) {
			set = new HashSet<Integer>();

			if (!rbt.contains((T) db[i].getMovie_title())) {
				set.add(db[i].getId());
				rbt.put((T) db[i].getMovie_title(), set);

			}

			else {
				HashSet<Integer> insert = rbt.get((T) db[i].getMovie_title());
				insert.add(db[i].getId());
				rbt.put((T) db[i].getMovie_title(), insert);
			}
		}
	}
	/*
	 * This method takes the string field, RBT, and a HashSet to insert a Red Black Tree into the HashMap
	 * where the keys will be the different directors, and the Values will be a set of movie IDs with that director.
	 */
	private void addFieldDirector(String field, RedBlackTree<T, HashSet<Integer>> rbt, HashSet<Integer> set) {
		for (int i = 0; i < n; i++) {
			set = new HashSet<Integer>();

			if (!rbt.contains((T) db[i].getDirector_name())) {
				set.add(db[i].getId());
				rbt.put((T) db[i].getDirector_name(), set);

			}

			else {
				HashSet<Integer> insert = rbt.get((T) db[i].getDirector_name());
				insert.add(db[i].getId());
				rbt.put((T) db[i].getDirector_name(), insert);
			}
		}
	}
	/*
	 * This method takes the string field, RBT, and a HashSet to insert a Red Black Tree into the HashMap
	 * where the keys will be the different 1st actors, and the Values will be a set of movie IDs with that 
	 * actor as actor 1.
	 */
	private void addFieldActor1(String field, RedBlackTree<T, HashSet<Integer>> rbt, HashSet<Integer> set) {
		for (int i = 0; i < n; i++) {
			set = new HashSet<Integer>();

			if (!rbt.contains((T) db[i].getActor_1_name())) {
				set.add(db[i].getId());
				rbt.put((T) db[i].getActor_1_name(), set);

			}

			else {
				HashSet<Integer> insert = rbt.get((T) db[i].getActor_1_name());

				insert.add(db[i].getId());
				rbt.put((T) db[i].getActor_1_name(), insert);
			}
		}
	}
	/*
	 * This method takes the string field, RBT, and a HashSet to insert a Red Black Tree into the HashMap
	 * where the keys will be the different 2nd actors, and the Values will be a set of movie IDs with that 
	 * actor as actor 2.
	 */
	private void addFieldActor2(String field, RedBlackTree<T, HashSet<Integer>> rbt, HashSet<Integer> set) {
		for (int i = 0; i < n; i++) {
			set = new HashSet<Integer>();

			if (!rbt.contains((T) db[i].getActor_2_name())) {
				set.add(db[i].getId());
				rbt.put((T) db[i].getActor_2_name(), set);

			}

			else {
				HashSet<Integer> insert = rbt.get((T) db[i].getActor_2_name());

				insert.add(db[i].getId());
				rbt.put((T) db[i].getActor_2_name(), insert);
			}
		}
	}
	/*
	 * This method takes the string field, RBT, and a HashSet to insert a Red Black Tree into the HashMap
	 * where the keys will be the different 3rd actors, and the Values will be a set of movie IDs with that 
	 * actor as actor3.
	 */
	private void addFieldActor3(String field, RedBlackTree<T, HashSet<Integer>> rbt, HashSet<Integer> set) {
		for (int i = 0; i < n; i++) {
			set = new HashSet<Integer>();

			if (!rbt.contains((T) db[i].getActor_3_name())) {
				set.add(db[i].getId());
				rbt.put((T) db[i].getActor_3_name(), set);

			}

			else {
				HashSet<Integer> insert = rbt.get((T) db[i].getActor_3_name());

				insert.add(db[i].getId());
				rbt.put((T) db[i].getActor_3_name(), insert);
			}
		}

	}
	/*
	 * This method takes the string field, RBT, and a HashSet to insert a Red Black Tree into the HashMap
	 * where the keys will be the different languages, and the Values will be a set of movie IDs with that 
	 * language.
	 */
	private void addFieldLanguage(String field, RedBlackTree<T, HashSet<Integer>> rbt, HashSet<Integer> set) {
		for (int i = 0; i < n; i++) {
			set = new HashSet<Integer>();

			if (!rbt.contains((T) db[i].getLanguage())) {
				set.add(db[i].getId());
				rbt.put((T) db[i].getLanguage(), set);

			}

			else {
				HashSet<Integer> insert = rbt.get((T) db[i].getLanguage());

				insert.add(db[i].getId());
				rbt.put((T) db[i].getLanguage(), insert);
			}
		}

	}
	/*
	 * This method takes the string field, RBT, and a HashSet to insert a Red Black Tree into the HashMap
	 * where the keys will be in which country the movie was made, and the Values will be a set of movie IDs 
	 * that were made in the same country.
	 */
	private void addFieldCountry(String field, RedBlackTree<T, HashSet<Integer>> rbt, HashSet<Integer> set) {
		for (int i = 0; i < n; i++) {
			set = new HashSet<Integer>();

			if (!rbt.contains((T) db[i].getCountry())) {
				set.add(db[i].getId());
				rbt.put((T) db[i].getCountry(), set);

			}

			else {
				HashSet<Integer> insert = rbt.get((T) db[i].getCountry());

				insert.add(db[i].getId());
				rbt.put((T) db[i].getCountry(), insert);
			}
		}

	}
	/*
	 * This method takes the string field, RBT, and a HashSet to insert a Red Black Tree into the HashMap
	 * where the keys will be the different content ratings, and the Values will be a set of movie IDs with that 
	 *  same content ratings.
	 */
	private void addFieldContentRating(String field, RedBlackTree<T, HashSet<Integer>> rbt, HashSet<Integer> set) {
		for (int i = 0; i < n; i++) {
			set = new HashSet<Integer>();

			if (!rbt.contains((T) db[i].getContent_rating())) {
				set.add(db[i].getId());
				rbt.put((T) db[i].getContent_rating(), set);

			}

			else {
				HashSet<Integer> insert = rbt.get((T) db[i].getContent_rating());

				insert.add(db[i].getId());
				rbt.put((T) db[i].getContent_rating(), insert);
			}
		}

	}

}
