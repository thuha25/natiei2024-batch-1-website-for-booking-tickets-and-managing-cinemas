package cinemas.repositories.impl;

import cinemas.models.Theater;
import cinemas.repositories.TheaterRepository;
import org.springframework.stereotype.Repository;

@Repository("theaterRepository")
public class TheaterRepositoryImpl extends BaseRepositoryImpl<Theater, Integer> implements TheaterRepository {
    public TheaterRepositoryImpl() {
        super(Theater.class);
    }
}
