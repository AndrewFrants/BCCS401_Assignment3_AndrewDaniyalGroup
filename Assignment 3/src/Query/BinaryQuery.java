/**
 * 
 */
package Query;

/**
 * @author andreyf
 * @param <T>
 * Example: Not
 */
public abstract class BinaryQuery<T extends Comparable<T>> extends UnaryQuery<T> {

	/*
	 * Left term
	 */
	protected Query<T> left;
	
	public BinaryQuery(Query<T> right, Query<T> left) {
		super(right);
		this.left = left;
	}

}
