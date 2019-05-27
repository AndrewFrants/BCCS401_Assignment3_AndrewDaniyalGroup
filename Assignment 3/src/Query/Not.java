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
public final class Not<T extends Comparable<T>> extends UnaryQuery<T> {

	public Not(Query<T> expr) {
		super(expr);
	}

	private HashSet<Integer> notTransform(Map<String, RedBlackTree<T, HashSet<Integer>>> indexTreeMap)
	{
		RedBlackTree<T, HashSet<Integer>> allItems = indexTreeMap.get(this.right.getType());
		
		HashSet<Integer> transform = new HashSet<Integer>();
		HashSet<Integer> not = new HashSet<Integer>();
		
		if (this.right != null)
		{
			not = this.right.execute(indexTreeMap);
		}
		
		if (allItems != null) {
			for (T ri : allItems.keys())
			{
				for(Integer midx : allItems.get(ri))
				{
					if (!not.contains(midx))
					{
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
