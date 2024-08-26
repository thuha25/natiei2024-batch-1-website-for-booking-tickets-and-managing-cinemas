package cinemas.services;

import cinemas.dtos.BookingDto;
import cinemas.dtos.FoodSelectionFormDto;
import cinemas.dtos.PaginationResult;
import cinemas.dtos.SeatSelectionFormDto;
import cinemas.exceptions.BookingNotFoundException;
import cinemas.exceptions.UserNotFoundException;
import cinemas.models.Booking;

import java.util.List;

public interface BookingsService {
    Booking createBooking(SeatSelectionFormDto seatSelectionFormDto, FoodSelectionFormDto foodSelectionFormDto, Integer pointUsed, Integer userId, Integer showtimeId) throws UserNotFoundException;
    void updateBookingOnPaymentSuccess(Integer bookingId) throws BookingNotFoundException;
    BookingDto getBookingDtoById(Integer bookingId, Integer userId) throws BookingNotFoundException;
    PaginationResult<BookingDto> getPagnitionValidBookingsDtoOfUser(int userId, int page, int size);
    void createBookingCancel(Integer userId, Integer bookingId, String reason) throws BookingNotFoundException;
    PaginationResult<Booking> getPaginationBookingsByIdOrCustomerName(String keyword, int page, int size);
}
