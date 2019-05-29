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
public final class Not<T extends Comparable<T>> extends UnaryQuery<T> {

	public Not(Query<T> expr) {
		super(expr);
	}

	private HashSet<Integer> notTransform(Map<String, RedBlackTree<T, HashSet<Integer>>> indexTreeMap)
	{
		RedBlackTree<T, HashSet<Integer>> allItems = indexTreeMap.get(this.right.getType());
		
		HashSet<Integer> transform = new HashSet<Integer>();
		HashSet<Integer> not = this.right.execute(indexTreeMap);

		if (not == null) {
			for (T ri : allItems.keys()) {

				for (Integer midx : allItems.get(ri)) {

					transform.add(midx);
				}

			}
		} else {
			for (T ri : allItems.keys()) {

				for (Integer midx : allItems.get(ri)) {

					if (!not.contains(midx)) {
						transform.add(midx);
					}

				}

			}
		}

		return transform;
	}

	@Override
	public HashSet<Integer> execute(Map<String, RedBlackTree<T, HashSet<Integer>>> indexTreeMap) {
		return notTransform(indexTreeMap);
	}

	@Override
	public String getType() {

		return this.right.getType();
	}

	@Override
	public T getValue() {
		return this.right.getValue();
	}

}