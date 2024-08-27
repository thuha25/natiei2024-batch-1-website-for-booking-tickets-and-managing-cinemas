package cinemas.repositories;

import cinemas.models.Showtime;

import java.time.LocalDate;
import java.util.List;

public interface ShowtimesRepository extends BaseRepository<Showtime, Integer> {
    List<Showtime> getShowtimeByDateAndCity(int movieId, int cityId, LocalDate date);
    List<Showtime> getShowtimeByTheaterAndDate(int theaterId, LocalDate date);
    List<Showtime> getShowtimeByTheaterAndDateWithStartTimeAsc(int theaterId, LocalDate date);
}
