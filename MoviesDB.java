import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;

public class MoviesDB<T extends Comparable<T>> {

	private String fileName;
	private Map<String, RedBlackTree<T, HashSet<Integer>>> indexTreeMap = new HashMap<String, RedBlackTree<T, HashSet<Integer>>>();

	private Movie[] db;
	private int n = 0;

	public MoviesDB(String fileName) throws IOException {

		BufferedReader br = new BufferedReader(new FileReader(fileName));

		int i = 0;
		String str;
		String[] test;
		Movie mov;
		while ((str = br.readLine()) != null) {
			n++;
		}
		br.close();
		n = n - 1;
		System.out.println(n);
		br = new BufferedReader(new FileReader(fileName));
		db = new Movie[n];
		while ((str = br.readLine()) != null && i < n) {
			mov = new Movie();
			test = str.split(",");
			if (i > 0) {

				mov.setId(Integer.parseInt(test[0]));
				mov.setColor(test[1]);
				mov.setMovie_title(test[2]);
				mov.setDuration(test[3]);
				mov.setDirector_name(test[4]);
				mov.setActor_1_name(test[5]);
				mov.setActor_2_name(test[6]);
				mov.setActor_3_name(test[7]);
				mov.setMovie_imdb_link(test[8]);
				mov.setLanguage(test[9]);
				mov.setCountry(test[10]);
				mov.setContent_rating(test[11]);
				mov.setTitle_year(test[12]);
			
				try {
					if(Double.parseDouble(test[13]) < 10.0)
				mov.setImdb_score(Double.parseDouble((test[13])));
					else {
						mov.setImdb_score(-1.0);
					}
				}
				catch(Exception e) {
					mov.setImdb_score(-1.0);
				}
			
				
				
				
				db[i - 1] = mov;
			}
			i++;
		}
		br.close();

	}

	public void addFieldIndex(String field) {
		RedBlackTree<T, HashSet<Integer>> rbt= new RedBlackTree<T, HashSet<Integer>>();;

		HashSet<Integer> set;

		if (field == "year") {
	
			for (int i = 0; i < n-1; i++) {
				set = new HashSet<Integer>();

				if (!rbt.contains((T)db[i].getTitle_year())) {
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

		}

		if (field == "score") {

			for (int i = 0; i < n-1; i++) {
				set = new HashSet<Integer>();

				if (!rbt.contains((T)db[i].getImdb_score())) {
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

		}
	}
	
	public void print(int index) {
		System.out.println(db[index - 1].getMovie_title());
	}

	public Map<String, RedBlackTree<T, HashSet<Integer>>> getIndexTreeMap() {
		return indexTreeMap;
	}

}
