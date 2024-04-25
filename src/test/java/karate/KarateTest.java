package karate;

import com.intuit.karate.junit5.Karate;

public class KarateTest {
	
	@Karate.Test
    Karate testMovies() {
        return Karate.run("classpath:movies.feature");
    }
}
