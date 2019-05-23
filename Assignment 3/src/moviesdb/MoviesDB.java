/**
 * 
 */
package moviesdb;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;

/**
 * @author andreyf
 *
 */
public class MoviesDB<T extends Comparable<T>> {

	public String filename;
	private Map<String, RedBlackTree<T, HashSet<Integer>>> indexTreeMap = new HashMap<String, RedBlackTree<T, HashSet<Integer>>>();
	
	private Movie[] db;
	
	private int n;
	
	public MoviesDB(String string) throws FileNotFoundException {
		// TODO Auto-generated constructor stub
	}

	// create a new red black tree by field
	public void addFieldIndex(String field) {
		
	}
	
	public Map<String, RedBlackTree<T, HashSet<Integer>>> getIndexTreeMap() {
		return null;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) throws FileNotFoundException {
		// TODO Auto-generated method stub
		  MoviesDB movieDB = new MoviesDB("simple.csv");
		  movieDB.addFieldIndex("year");
		  movieDB.addFieldIndex("imdb_score");
		  Query<Integer> query = new And<Integer>(new Equal("year",2012), new Equal("imdb_score",6.1));

		  HashSet<Integer> result = (HashSet<Integer>) query.execute(movieDB.getIndexTreeMap());
		  
		  if(result!=null) {
			  System. out .println(result); 
		  }
		  
		  Iterator<Integer> idIterator = result.iterator();
		  
		  while(idIterator.hasNext()) {
			  int id = idIterator.next();
			  movieDB.print(id);
		  }
	}

	private void print(int id) {
		// TODO Auto-generated method stub
		
	}

}
