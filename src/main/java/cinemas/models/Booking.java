package cinemas.models;

import cinemas.enums.BookingStatusEnum;
import cinemas.models.common.CreationUpdationAuditableEntity;
import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "bookings")
public class Booking extends CreationUpdationAuditableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "customer_name")
    private String customerName;
    private Integer amount;
    @Enumerated(EnumType.ORDINAL)
    private BookingStatusEnum status = BookingStatusEnum.BOOKED;

    @ManyToOne
    @JoinColumn(name = "showtime_id")
    private Showtime showtime;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private User customer;

    @OneToMany(mappedBy = "booking")
    private Set<ShowtimeSeat> bookingSeats;

    @OneToMany(mappedBy = "booking", cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private Set<BookingFood> bookingFoods;

    @Column(name = "point_used")
    private Integer pointUsed = 0;

    // Getters and Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public BookingStatusEnum getStatus() {
        return status;
    }

    public void setStatus(BookingStatusEnum status) {
        this.status = status;
    }

    public Showtime getShowtime() {
        return showtime;
    }

    public void setShowtime(Showtime showtime) {
        this.showtime = showtime;
    }

    public User getCustomer() {
        return customer;
    }

    public void setCustomer(User customer) {
        this.customer = customer;
    }

    public Set<ShowtimeSeat> getBookingSeats() {
        return bookingSeats;
    }

    public void setBookingSeats(Set<ShowtimeSeat> bookingSeats) {
        this.bookingSeats = bookingSeats;
    }

    public Set<BookingFood> getBookingFoods() {
        return bookingFoods;
    }

    public void setBookingFoods(Set<BookingFood> bookingFoods) {
        this.bookingFoods = bookingFoods;
    }

    public Integer getPointUsed() {
        return pointUsed;
    }

    public void setPointUsed(Integer pointUsed) {
        this.pointUsed = pointUsed;
    }
}
