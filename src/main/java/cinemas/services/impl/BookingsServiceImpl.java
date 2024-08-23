package cinemas.services.impl;

import cinemas.dtos.*;
import cinemas.enums.BookingStatusEnum;
import cinemas.enums.RoleEnum;
import cinemas.exceptions.BookingNotFoundException;
import cinemas.exceptions.UserNotFoundException;
import cinemas.models.*;
import cinemas.repositories.BookingsRepository;
import cinemas.repositories.ShowtimeSeatsRepository;
import cinemas.repositories.ShowtimesRepository;
import cinemas.repositories.UsersRepository;
import cinemas.services.BookingsService;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BookingsServiceImpl implements BookingsService {
    private BookingsRepository bookingsRepository;
    private UsersRepository usersRepository;
    private ShowtimesRepository showtimesRepository;
    private ShowtimeSeatsRepository showtimeSeatsRepository;
    private Logger logger = org.slf4j.LoggerFactory.getLogger(BookingsServiceImpl.class);

    public BookingsServiceImpl(BookingsRepository bookingsRepository,
                               UsersRepository usersRepository,
                               ShowtimesRepository showtimesRepository,
                               ShowtimeSeatsRepository showtimeSeatsRepository) {
        this.bookingsRepository = bookingsRepository;
        this.usersRepository = usersRepository;
        this.showtimesRepository = showtimesRepository;
        this.showtimeSeatsRepository = showtimeSeatsRepository;
    }

    @Override
    public Booking createBooking(SeatSelectionFormDto seatSelectionForm, FoodSelectionFormDto foodSelectionForm, Integer pointUsed, Integer userId, Integer showtimeId) throws UserNotFoundException {
        int amount = seatSelectionForm.getTotalPrice() + foodSelectionForm.getTotalPrice() - pointUsed * 1000;
        var booking = new Booking();
        booking.setAmount(amount);
        booking.setPointUsed(pointUsed);
        Set<BookingFood> bookingFoods = new HashSet<>();
        for (var foodSelection : foodSelectionForm.getFoodSelections()) {
            if (foodSelection.getCount() == 0) {
                continue;
            }
            var bookingFood = new BookingFood();
            bookingFood.setFood(foodSelection.getFood());
            bookingFood.setFoodName(foodSelection.getFood().getName());
            bookingFood.setBooking(booking);
            bookingFood.setFoodCount(foodSelection.getCount());
            bookingFood.setFoodPrice(foodSelection.getFood().getPrice());
            bookingFoods.add(bookingFood);
        }
        User user = usersRepository.findById(userId).orElse(null);
        if (user == null) {
            throw new UserNotFoundException();
        }
        Showtime showtime = showtimesRepository.findById(showtimeId).orElse(null);
        if (pointUsed > user.getRewardPoints() || pointUsed < 0) {
            throw new IllegalArgumentException("Point used not valid!");
        }
        user.setRewardPoints(user.getRewardPoints() - pointUsed);
        booking.setBookingFoods(bookingFoods);
        booking.setStatus(BookingStatusEnum.BOOKED);
        booking.setCustomer(user);
        booking.setShowtime(showtime);
        booking.setCustomerName(user.getFullName());
        Booking bookingSaved = bookingsRepository.save(booking);
        for (var selectionSeat : seatSelectionForm.getSeatSelectionsDto()) {
            showtimeSeatsRepository.updateShowtimeSeat(showtimeId, userId, selectionSeat.getSeat().getId(), bookingSaved.getId());
        }
        return bookingSaved;
    }

    @Override
    public void updateBookingOnPaymentSuccess(Integer bookingId) throws BookingNotFoundException {
        Booking booking = bookingsRepository.findById(bookingId).orElse(null);
        if (booking == null) {
            throw new BookingNotFoundException();
        }
        booking.setStatus(BookingStatusEnum.PAID);
    }

    @Override
    public BookingDto getBookingDtoById(Integer bookingId, Integer userId) throws BookingNotFoundException {
        Booking booking = null;
        try {
            booking = bookingsRepository.findBookingByUser(userId, bookingId);
        } catch (Throwable e) {
            logger.error("Error when getting booking by user", e);
        }
        if (booking == null) {
            throw new BookingNotFoundException();
        }
        return new BookingDto(booking);
    }

    @Override
    public PaginationResult<BookingDto> getPagnitionValidBookingsDtoOfUser(int userId, int page, int size) {
        Pageable pageable = new Pageable(page, size);
        var validBookingStatus = new BookingStatusEnum[]{BookingStatusEnum.REFUNDED, BookingStatusEnum.PRINTED, BookingStatusEnum.PAID};
        var bookings = bookingsRepository.findBookingsByUserAndStatusWithCreatedDesc(userId, validBookingStatus, pageable);
        var totalElements = bookingsRepository.countBookingsByUserAndStatus(userId, validBookingStatus);
        List<BookingDto> bookingsDto = new ArrayList<>();
        for (Booking booking : bookings) {
            BookingDto bookingDto = new BookingDto(booking);
            bookingsDto.add(bookingDto);
        }
        return new PaginationResult<>(totalElements, size, bookingsDto);
    }

    @Override
    public void createBookingCancel(Integer userId, Integer bookingId, String reason) throws BookingNotFoundException {
        Booking booking = null;
        try {
            booking = bookingsRepository.findBookingByUser(userId, bookingId);
        } catch (Throwable e) {
            logger.error("Error when getting booking by user", e);
        }
        if (booking == null) {
            throw new BookingNotFoundException();
        }
        booking.setStatus(BookingStatusEnum.REFUNDED);
        var user = usersRepository.findById(userId).get();
        if (user.getRole() == RoleEnum.CUSTOMER) {
            user.setRewardPoints(user.getRewardPoints() + booking.getAmount() / 1000);
        }
        var bookingRefund = new BookingRefund();
        bookingRefund.setBooking(booking);
        bookingRefund.setRefundedReason(reason);
        bookingRefund.setRefundedByUser(user);
        booking.setBookingRefund(bookingRefund);

        bookingsRepository.save(booking);
    }

    @Override
    public PaginationResult<Booking> getPaginationBookingsByIdOrCustomerName(String keyword, int page, int size) {
        var pageable = new Pageable(page, size);
        var bookings = bookingsRepository.getBookingsByIdOrCustomerNameWithCreatedDesc(keyword, pageable);
        var totalElements = bookingsRepository.countBookingsByIdOrCustomerName(keyword);
        return new PaginationResult<>(totalElements, size, bookings);
    }

}
