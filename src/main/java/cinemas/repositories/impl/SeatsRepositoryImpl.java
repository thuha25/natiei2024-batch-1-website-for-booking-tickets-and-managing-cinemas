package cinemas.repositories.impl;

import cinemas.models.Seat;
import cinemas.repositories.SeatsRepository;
import org.springframework.stereotype.Repository;

@Repository("seatsRepository")
public class SeatsRepositoryImpl extends BaseRepositoryImpl<Seat, Integer> implements SeatsRepository {
    public SeatsRepositoryImpl() {
        super(Seat.class);
    }
}
