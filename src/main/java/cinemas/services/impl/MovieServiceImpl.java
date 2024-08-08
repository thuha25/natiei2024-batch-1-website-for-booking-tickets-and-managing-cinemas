package cinemas.services.impl;

import cinemas.models.Movie;
import cinemas.repositories.MovieRepository;
import cinemas.services.MovieService;

import java.util.List;

public class MovieServiceImpl implements MovieService {
    private MovieRepository movieRepository;

    public MovieServiceImpl(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @Override
    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }
}
