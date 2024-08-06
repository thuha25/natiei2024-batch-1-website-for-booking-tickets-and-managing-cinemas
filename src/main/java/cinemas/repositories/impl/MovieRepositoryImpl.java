package cinemas.repositories.impl;

import cinemas.models.Movie;
import cinemas.repositories.MovieRepository;
import org.springframework.stereotype.Repository;

@Repository("movieRepository")
public class MovieRepositoryImpl extends BaseRepositoryImpl<Movie, Integer> implements MovieRepository {
    public MovieRepositoryImpl() {
        super(Movie.class);
    }
}
