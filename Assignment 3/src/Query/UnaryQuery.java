/**
 * 
 */
package Query;

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
