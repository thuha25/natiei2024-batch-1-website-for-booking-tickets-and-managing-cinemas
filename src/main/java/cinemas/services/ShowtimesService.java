package cinemas.services;

import cinemas.dtos.ShowtimeByTheaterDto;
import cinemas.models.Movie;
import cinemas.models.Screen;
import cinemas.models.Showtime;
import cinemas.models.Theater;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface ShowtimesService {
    Showtime save(Showtime showtime);
    List<Showtime> getShowtimeByDateAndCity(int movieId, int cityId, LocalDate date);
    List<Map<String, String>> generateFormattedDates(LocalDate startDate, int daysRange);
    Map<Theater, Map<Screen, List<Showtime>>> getGroupedShowtimesByTheater(int movieId, int cityId, LocalDate startDate);
    Showtime findById(int showtimeId);
    List<ShowtimeByTheaterDto> getShowtimeByTheater(int theaterId, LocalDate date);
    Map<Movie, Map<Screen, List<ShowtimeByTheaterDto>>> groupByMovieAndScreen(List<ShowtimeByTheaterDto> showtimeDtos);
}
