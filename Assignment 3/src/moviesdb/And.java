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

/*
 * This class creates a Binary And query where two queries, or nested queries, must be met and generates
 * the HashTable of movie id's where both queries are met. 
 */
public final class And<T extends Comparable<T>> extends ManyQueries<T> {

	public And(Query<T>... query) {
		super(query);
	}

	private HashSet<Integer> andCombine(HashSet<Integer> left, HashSet<Integer> right)
	{
		HashSet<Integer> composite = new HashSet<Integer>();
		
		if (right == null || left == null)
			return composite;
		
		for (Integer ri : right)
		{
			if (left.contains(ri))
			{
				composite.add(ri);
			}
		}
		
		return composite;
	}
	
	/*
	 * executes the query
	 */
	@Override
	public HashSet<Integer> execute(Map<String, RedBlackTree<T, HashSet<Integer>>> indexTreeMap) {
		
		HashSet<Integer> composite = this.queries[0].execute(indexTreeMap);
		
		for(Query<T> q : queries) {
			composite = andCombine(composite, q.execute(indexTreeMap));
		}
		
		return composite;
	}
	
	@Override
	public String getType() {
		return this.queries[0].getType();
	}

	@Override
	public T getValue() {
		return this.queries[0].getValue();
	}
}
