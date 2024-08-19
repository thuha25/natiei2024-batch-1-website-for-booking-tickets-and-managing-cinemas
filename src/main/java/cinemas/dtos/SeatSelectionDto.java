package cinemas.dtos;

import cinemas.models.Seat;

public class SeatSelectionDto {
    private Seat seat;
    private int price;

    public Seat getSeat() {
        return seat;
    }

    public void setSeat(Seat seat) {
        this.seat = seat;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
