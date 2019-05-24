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
public class Equal<T extends Comparable<T>> implements Query<T> {
	
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

	public Equal(String type, T val) {
		this.type = type;
		this.value = val;
	}

	@Override
	public HashSet<Integer> execute(Map<String, RedBlackTree<T, HashSet<Integer>>> indexTreeMap) {
		RedBlackTree<T, HashSet<Integer>> rb = indexTreeMap.get(type);
		
		return rb.get(value);
	}



}