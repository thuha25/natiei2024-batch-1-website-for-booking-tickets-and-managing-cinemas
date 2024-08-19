package cinemas.services;

import cinemas.dtos.BookingDto;
import cinemas.dtos.FoodSelectionFormDto;
import cinemas.dtos.SeatSelectionFormDto;
import cinemas.exceptions.BookingNotFoundException;
import cinemas.exceptions.UserNotFoundException;
import cinemas.models.Booking;

import java.util.List;

public interface BookingsService {
    Booking createBooking(SeatSelectionFormDto seatSelectionFormDto, FoodSelectionFormDto foodSelectionFormDto, Integer pointUsed, Integer userId, Integer showtimeId) throws UserNotFoundException;
    void updateBookingOnPaymentSuccess(Integer bookingId) throws BookingNotFoundException;
    BookingDto getBookingDtoById(Integer bookingId, Integer userId) throws BookingNotFoundException;
    List<BookingDto> getBookingsDto(int userId);
}
