package cinemas.dtos;

import cinemas.enums.BookingStatusEnum;
import cinemas.models.*;
import cinemas.utils.BookingUtils;

import java.time.ZonedDateTime;
import java.util.Set;
import java.util.stream.Collectors;

public class BookingDto {
    private int id;
    private Movie movie;
    private Showtime showtime;
    private Theater theater;
    private Screen screen;
    private Set<ShowtimeSeat> seats;
    private Set<BookingFood> bookingFoods;
    private int amount;
    private int point;
    private int totalFoodPrice;
    private int totalSeatPrice;
    private BookingStatusEnum status;
    private ZonedDateTime createdAt;
    private BookingRefund bookingRefund;

    public BookingDto(Booking booking) {
        this.id = booking.getId();
        this.movie = booking.getShowtime().getMovie();
        this.showtime = booking.getShowtime();
        this.screen = booking.getShowtime().getScreen();
        this.theater = booking.getShowtime().getScreen().getTheater();
        this.seats = booking.getBookingSeats();
        this.bookingFoods = booking.getBookingFoods();
        this.amount = booking.getAmount();
        this.point = booking.getPointUsed();
        this.status = booking.getStatus();
        this.createdAt = booking.getCreatedAt();
        if (booking.getStatus() == BookingStatusEnum.REFUNDED) {
            this.bookingRefund = booking.getBookingRefund();
        }
        calculateTotalFoodPrice();
        calculateTotalSeatPrice();
    }

    public BookingRefund getBookingRefund() {
        return bookingRefund;
    }

    public void setBookingRefund(BookingRefund bookingRefund) {
        this.bookingRefund = bookingRefund;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public BookingStatusEnum getStatus() {
        return status;
    }

    public void setStatus(BookingStatusEnum status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public Movie getMovie() {
        return movie;
    }

    public Showtime getShowtime() {
        return showtime;
    }

    public Theater getTheater() {
        return theater;
    }

    public Screen getScreen() {
        return screen;
    }

    public int getAmount() {
        return amount;
    }

    public int getPoint() {
        return point;
    }

    public int getTotalFoodPrice() {
        return totalFoodPrice;
    }

    public int getTotalSeatPrice() {
        return totalSeatPrice;
    }

    public String getSeats() {
        String joinSeatNames = seats.stream()
                .map(showtimeSeat -> showtimeSeat.getSeat().getName())
                .collect(Collectors.joining(", "));
        return joinSeatNames;
    }


    public Set<BookingFood> getBookingFoods() {
        return bookingFoods;
    }

    public void calculateTotalSeatPrice() {
        for (ShowtimeSeat showtimeSeat : seats) {
            totalSeatPrice += BookingUtils.getSeatPrice(showtimeSeat.getSeat(), showtime);
        }
    }

    public void calculateTotalFoodPrice() {
        for (BookingFood bookingFood : bookingFoods) {
            totalFoodPrice += (bookingFood.getFoodCount() * bookingFood.getFoodPrice());
        }
    }

    public int getDiscount() {
        return point * 1000;
    }

    public int getFinalPrice() {
        return totalFoodPrice + totalSeatPrice - getDiscount();
    }

    public boolean isCancelable() {
        /*
         * Check if booking not printed
         * And not within 60 minutes before showtime
         * */
        return status == BookingStatusEnum.PAID && showtime.getStartTime().minusMinutes(60).isAfter(ZonedDateTime.now());
    }
}
