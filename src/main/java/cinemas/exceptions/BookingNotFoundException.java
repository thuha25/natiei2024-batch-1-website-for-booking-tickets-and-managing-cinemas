package cinemas.exceptions;

import javassist.NotFoundException;

public class BookingNotFoundException extends NotFoundException {
    public BookingNotFoundException() {
        super("Booking not found");
    }
}
