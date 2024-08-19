package cinemas.exceptions;

import javassist.NotFoundException;

public class ShowtimeNotFoundException extends NotFoundException {
    public ShowtimeNotFoundException() {
        super("Showtime not found");
    }
}
