package cinemas.services.impl;

import cinemas.models.Movie;
import cinemas.repositories.MoviesRepository;
import cinemas.services.MoviesService;

import java.util.List;
import java.util.Optional;

public class MoviesServiceImpl implements MoviesService {
    private MoviesRepository moviesRepository;

    public MoviesServiceImpl(MoviesRepository moviesRepository) {
        this.moviesRepository = moviesRepository;
    }

    @Override
    public List<Movie> getAllMovies() {
        return moviesRepository.findAll();
    }
    @Override
    public Movie save(Movie movie) {
        return moviesRepository.save(movie);
    }

    @Override
    public Optional<Movie> findById(int id) {
        return moviesRepository.findById(id);
    }
}
