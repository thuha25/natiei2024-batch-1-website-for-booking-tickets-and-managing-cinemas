package cinemas.models;

import cinemas.converters.ZonedDateTimeConverter;
import cinemas.models.common.CreationUpdationAuditableEntity;
import jakarta.persistence.*;

import java.time.ZonedDateTime;
import java.util.Set;

@Entity
@Table(name = "showtimes")
public class Showtime extends CreationUpdationAuditableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Convert(converter = ZonedDateTimeConverter.class)
    @Column(name = "start_time")
    private ZonedDateTime startTime;
    @Column(name = "price_standard")
    private Integer priceStandard;
    @Column(name = "price_vip")
    private Integer priceVip;
    @Column(name = "price_couple")
    private Integer priceCouple;

    @ManyToOne
    @JoinColumn(name = "movie_id", insertable = false, updatable = false)
    private Movie movie;

    @ManyToOne
    @JoinColumn(name = "screen_id", insertable = false, updatable = false)
    private Screen screen;
    @ManyToMany(mappedBy = "showtimes")
    private Set<Seat> showtimeSeats;

    // Getters and Setters
    public Set<Seat> getShowtimeSeats() {
        return showtimeSeats;
    }

    public void setShowtimeSeats(Set<Seat> showtimeSeats) {
        this.showtimeSeats = showtimeSeats;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ZonedDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(ZonedDateTime startTime) {
        this.startTime = startTime;
    }

    public Integer getPriceStandard() {
        return priceStandard;
    }

    public void setPriceStandard(Integer priceStandard) {
        this.priceStandard = priceStandard;
    }

    public Integer getPriceVip() {
        return priceVip;
    }

    public void setPriceVip(Integer priceVip) {
        this.priceVip = priceVip;
    }

    public Integer getPriceCouple() {
        return priceCouple;
    }

    public void setPriceCouple(Integer priceCouple) {
        this.priceCouple = priceCouple;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public Screen getScreen() {
        return screen;
    }

    public void setScreen(Screen screen) {
        this.screen = screen;
    }
}
