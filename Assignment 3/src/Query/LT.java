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
 *
 */
public final class LT<T extends Comparable<T>> implements Query<T> {

	String type;
	T value;
	
	public void setType(String type) {
		this.type = type;
	}
	
	@Override
	public String getType() {
		return type;
	}
	
	public T getValue() {
		return value;
	}

	public void setValue(T value) {
		this.value = value;
	}
	
	public LT(String type, T val) {
		this.type = type;
		this.value = val;
	}

	@Override
	public HashSet<Integer> execute(Map<String, RedBlackTree<T, HashSet<Integer>>> indexTreeMap) {
		HashSet<Integer> compositeMovies = new HashSet<Integer>();
		
		RedBlackTree<T, HashSet<Integer>> rb = indexTreeMap.get(type);
		for (T set : rb.getKeys(value, true))
		{
			compositeMovies.addAll(rb.get(set));
		}
		
		return compositeMovies;
	}
}
