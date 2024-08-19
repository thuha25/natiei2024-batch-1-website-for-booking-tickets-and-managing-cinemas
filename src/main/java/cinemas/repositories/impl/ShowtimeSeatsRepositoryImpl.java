package cinemas.repositories.impl;

import cinemas.models.Seat;
import cinemas.models.ShowtimeSeat;
import cinemas.repositories.ShowtimeSeatsRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("showtimeSeatsRepository")
public class ShowtimeSeatsRepositoryImpl extends BaseRepositoryImpl<ShowtimeSeat, Integer> implements ShowtimeSeatsRepository {
    public ShowtimeSeatsRepositoryImpl(){
        super(ShowtimeSeat.class);
    }
    @Override
    public List<Seat> getSeatsByShowtimeAndScreen(int showtimeId) {
        String hql = "SELECT s FROM ShowtimeSeat ss JOIN ss.seat s WHERE ss.showtime.id = :showtimeId ORDER BY s.verticalIndex, s.horizontalIndex";
        return entityManager.createQuery(hql, Seat.class)
                .setParameter("showtimeId", showtimeId)
                .getResultList();
    }
    @Override
    public void updateShowtimeSeat(int showtimeId, int userId, int seatId, int bookingId){
        String hql = "UPDATE ShowtimeSeat ss SET ss.booking.id = :bookingId " +
                "WHERE ss.showtime.id = :showtimeId AND ss.seat.id = :seatId AND ss.user.id = :userId";

        entityManager.createQuery(hql)
                .setParameter("bookingId", bookingId)
                .setParameter("showtimeId", showtimeId)
                .setParameter("seatId", seatId)
                .setParameter("userId", userId)
                .executeUpdate();
    }
}
