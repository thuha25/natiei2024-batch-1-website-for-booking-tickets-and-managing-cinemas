package cinemas.repositories.impl;

import cinemas.models.Genre;
import cinemas.repositories.GenreRepository;
import org.springframework.stereotype.Repository;

@Repository("genreRepository")
public class GenreRepositoryImpl extends BaseRepositoryImpl<Genre, Integer> implements GenreRepository {
    public GenreRepositoryImpl() {
        super(Genre.class);
    }
}
