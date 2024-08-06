package cinemas.repositories.impl;

import cinemas.models.Showtime;
import cinemas.repositories.ShowtimeRepository;
import org.springframework.stereotype.Repository;

@Repository("showtimeRepository")
public class ShowtimeRepositoryImpl extends BaseRepositoryImpl<Showtime, Integer> implements ShowtimeRepository {
    public ShowtimeRepositoryImpl() {
        super(Showtime.class);
    }
}
