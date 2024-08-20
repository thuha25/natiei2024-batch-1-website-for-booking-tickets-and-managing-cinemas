package cinemas.repositories.impl;

import cinemas.models.Showtime;
import cinemas.repositories.ShowtimesRepository;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Repository("showtimesRepository")
public class ShowtimesRepositoryImpl extends BaseRepositoryImpl<Showtime, Integer> implements ShowtimesRepository {
    public ShowtimesRepositoryImpl() {
        super(Showtime.class);
    }

    @Override
    public List<Showtime> getShowtimeByDateAndCity(int movieId, int cityId, LocalDate selectedDate) {
        LocalDateTime startOfDay = LocalDateTime.of(selectedDate, LocalTime.now());
        LocalDateTime endOfDay = startOfDay.plusDays(1).minusNanos(1);

        String startDay = String.valueOf(startOfDay);
        String endDay = String.valueOf(endOfDay);

        String sql = "SELECT * FROM showtimes WHERE movie_id = :movieId " +
                "AND city_id = :cityId " +
                "AND start_time BETWEEN :startOfDay AND :endOfDay";

        Query query = entityManager.createNativeQuery(sql, Showtime.class);
        query.setParameter("movieId", movieId);
        query.setParameter("cityId", cityId);
        query.setParameter("startOfDay", startDay);
        query.setParameter("endOfDay", endDay);

        return query.getResultList();
    }
    @Override
    public List<Showtime> getShowtimeByTheaterAndDate(int theaterId, LocalDate date) {
        LocalDateTime startOfDay = LocalDateTime.of(date, LocalTime.now());
        LocalDateTime endOfDay = startOfDay.plusDays(1).minusNanos(1);

        String startDay = String.valueOf(startOfDay);
        String endDay = String.valueOf(endOfDay);

        String sql = "SELECT DISTINCT s.* " +
                "FROM showtimes s " +
                "JOIN screens sc ON s.screen_id = sc.id " +
                "JOIN theaters t ON sc.theater_id = t.id " +
                "WHERE t.id = :theaterId " +
                "AND s.start_time BETWEEN :startDate AND :endDate";

        Query query = entityManager.createNativeQuery(sql, Showtime.class);
        query.setParameter("theaterId", theaterId);
        query.setParameter("startDate", startDay);
        query.setParameter("endDate", endDay);

        return query.getResultList();
    }
}
