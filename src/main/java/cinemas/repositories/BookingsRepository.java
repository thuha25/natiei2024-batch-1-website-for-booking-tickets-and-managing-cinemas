package cinemas.repositories;

import cinemas.dtos.Pageable;
import cinemas.enums.BookingStatusEnum;
import cinemas.models.Booking;

import java.time.ZonedDateTime;
import java.util.List;

public interface BookingsRepository extends BaseRepository<Booking, Integer> {
    List<Booking> findPrintedBookingsSince(ZonedDateTime startDate, Integer theaterId);
    List<Booking> findPrintedBookingsSince(ZonedDateTime startDate);
    Booking findBookingByUser(int userId, int bookingId);
    List<Booking> findBookingsByUserAndStatusWithCreatedDesc(int userId, BookingStatusEnum[] statusEnums, Pageable pageable);
    Integer countBookingsByUserAndStatus(int userId, BookingStatusEnum[] statusEnums);
}
