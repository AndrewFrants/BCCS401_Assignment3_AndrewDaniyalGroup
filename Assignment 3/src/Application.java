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

import java.io.IOException;
import java.util.HashSet;

/*
 * This class creates a movieDB, adds the field types to query by, creates and executes the query, and prints
 * the results of the queries.
 */
public class Application {

	public static void main(String[] args) throws IOException {

		MoviesDB movieDB = new MoviesDB("movie_metadata.csv"); //movie Database object, using csv file
		
		//adds field criteria 
		movieDB.addFieldIndex("year");
		movieDB.addFieldIndex("score");
		movieDB.addFieldIndex("color");
		movieDB.addFieldIndex("content_rating");
		movieDB.addFieldIndex("act_2");
		
		//Creates a query
		Query<String> query = new And(new GT("score", 8.3), new LTE("year", 1990), 
				new Equal("content_rating", "PG"));
		//runs query and stores the movie id's is a hashtable
		HashSet<Integer> result = (HashSet<Integer>) query.execute(movieDB.getIndexTreeMap());

		//if no movies meet criteria
		if (result == null || result.isEmpty()) {
			System.out.println("No movies with that selected criteria");
		}
		
	
		else {
			System.out.println(result); //prints table of id's
			
			//prints movie info for all id's in query
			for (int i : result) {
				movieDB.print(i);
			}
		}

	}

}
