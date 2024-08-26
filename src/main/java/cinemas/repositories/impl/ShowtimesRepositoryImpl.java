package cinemas.repositories.impl;

import cinemas.models.Showtime;
import cinemas.repositories.ShowtimesRepository;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

import java.time.*;
import java.util.List;

@Repository("showtimesRepository")
public class ShowtimesRepositoryImpl extends BaseRepositoryImpl<Showtime, Integer> implements ShowtimesRepository {
    public ShowtimesRepositoryImpl() {
        super(Showtime.class);
    }

    @Override
    public List<Showtime> getShowtimeByDateAndCity(int movieId, int cityId, LocalDate selectedDate) {
        // Define the time zone
        ZoneId zoneId = ZoneId.systemDefault(); // Use your preferred time zone

        // Convert LocalDate to LocalDateTime with the start and end of the day
        LocalDateTime startOfDay = LocalDateTime.of(selectedDate, LocalTime.MIDNIGHT);
        LocalDateTime endOfDay = LocalDateTime.of(selectedDate.plusDays(1), LocalTime.MIDNIGHT);

        // Convert LocalDateTime to ZonedDateTime
        ZonedDateTime startZonedDateTime = startOfDay.atZone(zoneId);
        ZonedDateTime endZonedDateTime = endOfDay.atZone(zoneId);

        // Prepare HQL query with sorting
        String hql = "SELECT s " +
                "FROM Showtime s " +
                "WHERE s.movie.id = :movieId " +
                "AND s.city.id = :cityId " +
                "AND s.startTime >= :startOfDay " +
                "AND s.startTime < :endOfDay";

        // Create query
        TypedQuery<Showtime> query = entityManager.createQuery(hql, Showtime.class);
        query.setParameter("movieId", movieId);
        query.setParameter("cityId", cityId);
        query.setParameter("startOfDay", startZonedDateTime);
        query.setParameter("endOfDay", endZonedDateTime);

        // Execute query and return results
        return query.getResultList();
    }

    public List<Showtime> getShowtimeByTheaterAndDate(int theaterId, LocalDate date) {
        // Define the time zone
        ZoneId zoneId = ZoneId.systemDefault(); // Use your preferred time zone

        // Convert LocalDate to LocalDateTime with the start and end of the day
        LocalDateTime startOfDay = LocalDateTime.of(date, LocalTime.MIDNIGHT);
        LocalDateTime endOfDay = LocalDateTime.of(date.plusDays(1), LocalTime.MIDNIGHT);

        // Convert LocalDateTime to ZonedDateTime
        ZonedDateTime startZonedDateTime = startOfDay.atZone(zoneId);
        ZonedDateTime endZonedDateTime = endOfDay.atZone(zoneId);

        // Prepare HQL query
        String hql = "SELECT DISTINCT s " +
                "FROM Showtime s " +
                "JOIN s.screen sc " +
                "JOIN sc.theater t " +
                "WHERE t.id = :theaterId " +
                "AND s.startTime >= :startDate " +
                "AND s.startTime < :endDate";

        // Create query
        TypedQuery<Showtime> query = entityManager.createQuery(hql, Showtime.class);
        query.setParameter("theaterId", theaterId);
        query.setParameter("startDate", startZonedDateTime);
        query.setParameter("endDate", endZonedDateTime);

        // Execute query and return results
        return query.getResultList();
    }

    public List<Showtime> getShowtimeByTheaterAndDateWithStartTimeAsc(int theaterId, LocalDate date) {
        // Define the time zone
        ZoneId zoneId = ZoneId.systemDefault(); // Use your preferred time zone

        // Convert LocalDate to LocalDateTime with the start and end of the day
        LocalDateTime startOfDay = LocalDateTime.of(date, LocalTime.MIDNIGHT);
        LocalDateTime endOfDay = LocalDateTime.of(date.plusDays(1), LocalTime.MIDNIGHT);

        // Convert LocalDateTime to ZonedDateTime
        ZonedDateTime startZonedDateTime = startOfDay.atZone(zoneId);
        ZonedDateTime endZonedDateTime = endOfDay.atZone(zoneId);

        // Prepare HQL query
        String hql = "SELECT DISTINCT s " +
                "FROM Showtime s " +
                "JOIN s.screen sc " +
                "JOIN sc.theater t " +
                "WHERE t.id = :theaterId " +
                "AND s.startTime >= :startDate " +
                "AND s.startTime < :endDate " +
                "ORDER BY s.startTime ASC";

        // Create query
        TypedQuery<Showtime> query = entityManager.createQuery(hql, Showtime.class);
        query.setParameter("theaterId", theaterId);
        query.setParameter("startDate", startZonedDateTime);
        query.setParameter("endDate", endZonedDateTime);

        // Execute query and return results
        return query.getResultList();
    }
}
