package cinemas.dtos;

import java.time.LocalDate;
import java.time.LocalTime;

public class ShowtimeCreateFormDto {
    private Integer cityId;
    private Integer screenId;
    private Integer movieId;
    private LocalDate date;
    private LocalTime time;
    private Integer priceStandard;
    private Integer priceVip;

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
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

    public Integer getScreenId() {
        return screenId;
    }

    public void setScreenId(Integer screenId) {
        this.screenId = screenId;
    }

    public Integer getMovieId() {
        return movieId;
    }

    public void setMovieId(Integer movieId) {
        this.movieId = movieId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
