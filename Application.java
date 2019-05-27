import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashSet;

public class Application {

	public static void main(String[] args) throws IOException {
		
		
		HashSet<Integer> hash = new HashSet<Integer>();
		
		MoviesDB movieDB = new MoviesDB("movie_metadata.csv");
		movieDB.addFieldIndex("year");
		movieDB.addFieldIndex("score");
		Query<String> query = new GT("score", 9.3);
		HashSet<Integer> result = (HashSet<Integer>) query.execute(movieDB.getIndexTreeMap());
		
		if(result.isEmpty()) {
			System.out.println("No movies with that selected criteria");
		}
		for(int i : result) {
			System.out.print(i  + " "); movieDB.print(i);
		}

	}

}
