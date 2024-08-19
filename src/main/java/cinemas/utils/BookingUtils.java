package cinemas.utils;

import cinemas.dtos.SeatSelectionDto;
import cinemas.enums.SeatTypeEnum;
import cinemas.models.Seat;
import cinemas.models.Showtime;

public class BookingUtils {
    public static Integer getSeatPrice(Seat seat, Showtime showtime){
        if(seat.getType() == SeatTypeEnum.STANDARD){
            return showtime.getPriceStandard();
        }else{
            return showtime.getPriceVip();
        }
    }
}
