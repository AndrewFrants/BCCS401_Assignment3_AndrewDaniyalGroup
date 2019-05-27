/**
 * 
 */
package moviesdb;

import java.awt.List;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;

import Query.And;
import Query.Equal;
import Query.GT;
import Query.LT;
import Query.Not;
import Query.Or;
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
	
	public MoviesDB(String fileName) throws IOException {
		// TODO Auto-generated constructor stub
		
		/* test
		db = new Movie[5];
		db[0] = new Movie();
		db[0].setTitle_year(1999);
		db[1] = new Movie();
		db[1].setTitle_year(2000);
		db[2] = new Movie();
		db[2].setTitle_year(2001);
		db[3] = new Movie();
		db[3].setTitle_year(2001);
		db[4] = new Movie();
		db[4].setTitle_year(2001);
		*/
		loadMovies(fileName);
	}

	/*
	 * This function takes quotes as literals and works around the issue
	 * where the comma in a literally ends up being a separate column
	 * the comma is removed and the column is fixed up to be one column
	 */
	private static String[] fixUpCommasInQuotes(String str) {
		
		String[] movieAttributes = str.split(",");
		ArrayList<String> merge = new ArrayList<String>();
		merge.addAll(Arrays.asList(movieAttributes));
		
		if (movieAttributes.length > 14) // this is to handle cases where there is a comma in quotes ""
		{
			String[] movieQuotes = str.split("\"");
			int items_prior = 0;
			
			for (int c = 1; c < movieQuotes.length; c += 2)
			{
				items_prior += movieQuotes[c-1].split(",").length;
				int items_to_merge = movieQuotes[c].split(",").length;
				
				StringBuilder mergedstr = new StringBuilder();
				
				for (int m = 0; m < items_to_merge; m++) {
					mergedstr.append(merge.get(items_prior));
					merge.remove(items_prior);
				}
				
				merge.add(items_prior, mergedstr.toString());
			}
		}
		
		return merge.toArray(movieAttributes);
	}
	
	/*
	 * Loads movies from file
	 */
	private void loadMovies(String fileName) throws IOException {
		
		BufferedReader br = new BufferedReader(new FileReader(fileName));

		String str;
		String[] movieAttributes;
		Movie mov;
		while ((str = br.readLine()) != null) {
			n++;
		}
		br.close();
		n = n - 1;
		System.out.println(n);
		br = new BufferedReader(new FileReader(fileName));
		db = new Movie[n];
		for (int i = 0; (str = br.readLine()) != null && i < 50; i++) { // TODO remove up to 50 limit
			mov = new Movie();
			
			movieAttributes = str.split(",");
			
			if (movieAttributes.length > 14) { // handle the case where you get title like "I, Robot"
				movieAttributes = fixUpCommasInQuotes(str);
			}
			
			if (movieAttributes.length > 14) {
				throw new IOException("Too many columns, some data may be incorrect.");
			}
			
			int id = 0;
			
			try {
				id = Integer.parseInt(movieAttributes[0]);
			} catch (NumberFormatException ex) {
				System.out.println("Skipped " + i);
				continue; // skip this line
			}
			
			if (i > 0) {

				mov.setId(id);
				mov.setColor(movieAttributes[1]);
				mov.setMovie_title(movieAttributes[2]);
				mov.setDuration(safeConvertToInt(movieAttributes[3], 0));
				mov.setDirector_name(movieAttributes[4]);
				mov.setActor_1_name(movieAttributes[5]);
				mov.setActor_2_name(movieAttributes[6]);
				mov.setActor_3_name(movieAttributes[7]);
				mov.setMovie_imdb_link(movieAttributes[8]);
				mov.setLanguage(movieAttributes[9]);
				mov.setCountry(movieAttributes[10]);
				mov.setContent_rating(movieAttributes[11]);
				mov.setTitle_year(safeConvertToInt(movieAttributes[12], 1000));
				mov.setImdb_score(safeConvertToDouble(movieAttributes[13], 0.0));
				db[id] = mov;
			}
		}

		br.close();

	}
	
	private int safeConvertToInt(String value, int defaultVal) {
		
		if (value == null || value.isEmpty())
			return 0;
		
		try
		{
			return Integer.parseInt(value);
		} catch (NumberFormatException ex) {
			return defaultVal;
		}
	}
	

	private double safeConvertToDouble(String value, double defaultVal) {
		
		if (value == null || value.isEmpty())
			return 0;
		
		try
		{
			return Double.parseDouble(value);
		} catch (NumberFormatException ex) {
			return defaultVal;
		}
	}
	
	// create a new red black tree by field
	public void addFieldIndex(String field) {
		
		RedBlackTree<T, HashSet<Integer>> rbt = 
				new RedBlackTree<T, HashSet<Integer>>();
		HashSet<Integer> set;
	
		if(field == "getTitle_year") {
			for(int i = 1; i < 49; i++) { // TODO remove 49 limit
				set = new HashSet<Integer>();
				
				if(!rbt.contains((T)db[i].getTitle_year())) {
					set.add(db[i].getId());
					rbt.put((T)db[i].getTitle_year(), set);
				}
				
				else {
					HashSet<Integer> insert = rbt.get((T)db[i].getTitle_year());
					insert.add(db[i].getId());
					rbt.put((T)db[i].getTitle_year(), insert);
				}
			}
			
			indexTreeMap.put(field, rbt);
			
			//System.out.println(rbt.get((T)"2010"));

		}
		

		if (field == "getImdb_score") {
		for(int i = 1; i < 49;i++) {
			set = new HashSet<Integer>();
		
			
			if(!rbt.contains((T)db[i].getImdb_score())) {
				set.add(db[i].getId());
				rbt.put((T)db[i].getImdb_score(), set);
			}
			
			else {
				HashSet<Integer> insert = rbt.get((T)db[i].getImdb_score());
				insert.add(db[i].getId());
				rbt.put((T)db[i].getImdb_score(), insert);
			}
			
			
		}
		indexTreeMap.put(field, rbt);
		
		//System.out.println(rbt.get((T)"9.9"));
		//System.out.println(indexTreeMap);
	
	}
	}
	
	private HashSet<Integer> makeHashSet(int... indices)
	{
		HashSet<Integer> newHashSet = new HashSet<Integer>();
		
		for (int i : indices) {
			newHashSet.add(i);
	    }
		
		return newHashSet;
	}
	
	public Map<String, RedBlackTree<T, HashSet<Integer>>> getIndexTreeMap() {
		return indexTreeMap;
	}

	
	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		
		HashSet<Integer> hash = new HashSet<Integer>();
		
		MoviesDB movieDB = new MoviesDB("movie_metadata.csv");
		movieDB.addFieldIndex("getTitle_year");
		movieDB.addFieldIndex("getImdb_score");

		Query<Integer> query = new Or<Integer>(new Equal("getTitle_year",2001), new Not(new GT("getImdb_score",6.0)));
		
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
		System.out.print(id + " ");
		System.out.println(db[id].toString());
	}

}
