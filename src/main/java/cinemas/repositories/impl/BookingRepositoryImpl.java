package cinemas.repositories.impl;

import cinemas.models.Booking;
import cinemas.repositories.BookingRepository;
import org.springframework.stereotype.Repository;

@Repository("bookingRepository")
public class BookingRepositoryImpl extends BaseRepositoryImpl<Booking, Integer> implements BookingRepository {
    public BookingRepositoryImpl() {
        super(Booking.class);
    }
}
