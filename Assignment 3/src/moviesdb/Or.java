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

/**
 * @author andreyf
 * @param <T>
 *
 */
public final class Or<T extends Comparable<T>> extends ManyQueries<T> {

	public Or(Query<T>... query) {
		super(query);
	}

	private HashSet<Integer> orCombine(HashSet<Integer> left, HashSet<Integer> right)
	{
		@SuppressWarnings("unchecked") // we are literally cloning the same type
		HashSet<Integer> composite = new HashSet<Integer>();

		if (left != null)
		{
			composite.addAll(left);
		}
		
		if (right != null) {
			composite.addAll(right);
		}
		
		return composite;
	}
	
	@Override
	public HashSet<Integer> execute(Map<String, RedBlackTree<T, HashSet<Integer>>> indexTreeMap) {
	
		HashSet<Integer> composite = this.queries[0].execute(indexTreeMap);
		
		for(Query<T> q : queries) {
			composite = orCombine(composite, q.execute(indexTreeMap));
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
