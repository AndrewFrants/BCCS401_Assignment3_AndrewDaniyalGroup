/**
 * 
 */
package moviesdb;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;

import Query.And;
import Query.Equal;
import Query.Not;
import Query.Query;

/**
 * @author andreyf
 *
 */
public class MoviesDB<T extends Comparable<T>> {

	public String filename;
	
	// Type , RBT<Int or String depending on the type, HashSet<int> will have the list of moviews
	private Map<String, RedBlackTree<T, HashSet<Integer>>> indexTreeMap = new HashMap<String, RedBlackTree<T, HashSet<Integer>>>();
	
	// RedBlackTree will require lessThan, greaterThan methods
	
	// List of movies
	private Movie[] db;
	
	private int n;
	
	public MoviesDB(String string) throws FileNotFoundException {
		// TODO Auto-generated constructor stub
		
		db = new Movie[5];
		db[0] = new Movie(1999);
		db[1] = new Movie(2000);
		db[2] = new Movie(2001);
		db[3] = new Movie(2001);
		db[4] = new Movie(2001);
	}

	// create a new red black tree by field
	public void addFieldIndex(String field) {
		
	}
	
	private HashSet<Integer> makeHashSet(int... indices)
	{
		HashSet<Integer> newHashSet = new HashSet<Integer>();
		
		for (int i : indices) {
			newHashSet.add(i);
	    }
		
		return newHashSet;
	}
	
	public Map<String, RedBlackTree<Integer, HashSet<Integer>>> getIndexTreeMap() {
		
		Map<String, RedBlackTree<Integer, HashSet<Integer>>> map = new HashMap<String, RedBlackTree<Integer, HashSet<Integer>>>();
		RedBlackTree<Integer, HashSet<Integer>> rbYear = new RedBlackTree<Integer, HashSet<Integer>>();
		rbYear.put(1999, makeHashSet(0));
		rbYear.put(2000, makeHashSet(1));
		rbYear.put(2001, makeHashSet(2,3,4));
		
		map.put("year", rbYear);
		return map;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) throws FileNotFoundException {
		// TODO Auto-generated method stub
		  MoviesDB movieDB = new MoviesDB("simple.csv");
		  movieDB.addFieldIndex("year");
		  movieDB.addFieldIndex("imdb_score");
		  Query<Integer> query = new And<Integer>(new Equal("year",1999), new Not(new Equal("year",2000)));


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
