package cinemas.repositories;

import cinemas.models.Seat;
import cinemas.models.ShowtimeSeat;

import java.util.List;

public interface ShowtimeSeatsRepository extends BaseRepository<ShowtimeSeat, Integer>{
    List<Seat> getSeatsByShowtimeAndScreen(int showtimeId);
    void updateShowtimeSeat(int showtimeId, int userId, int seatId, int bookingId);
}
