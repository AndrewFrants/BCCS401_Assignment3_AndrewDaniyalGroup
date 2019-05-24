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
public final class Or<T extends Comparable<T>> extends BinaryQuery<T> {

	public Or(Query<T> left, Query<T> right) {
		super(left, right);
	}

	private HashSet<Integer> orCombine(HashSet<Integer> left, HashSet<Integer> right)
	{
		HashSet<Integer> composite = (HashSet<Integer>) left.clone();
		
		for (Integer ri : right)
		{
			if (!composite.contains(ri))
			{
				composite.add(ri);
			}
		}
		
		return composite;
	}
	
	@Override
	public HashSet<Integer> execute(Map<String, RedBlackTree<T, HashSet<Integer>>> indexTreeMap) {
		return orCombine(left.execute(indexTreeMap), right.execute(indexTreeMap));
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
