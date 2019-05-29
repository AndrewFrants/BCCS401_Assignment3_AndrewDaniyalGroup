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

import java.util.HashSet;
import java.util.Map;

import moviesdb.RedBlackTree;

/**
 * @author andreyf
 * @param <T>
 * Example: Not
 */
public abstract class UnaryQuery<T extends Comparable<T>> implements Query<T> {

	/*
	 * Right term
	 */
	protected Query<T> right;
	
	public UnaryQuery(Query<T> expr) {
		right = expr;
	}

	@Override
	public HashSet<Integer> execute(Map<String, RedBlackTree<T, HashSet<Integer>>> indexTreeMap) {
		// TODO Auto-generated method stub
		return null;
	}


}
