package cinemas.exceptions;

import javassist.NotFoundException;

public class MovieNotFoundException extends NotFoundException {
    public MovieNotFoundException() {
        super("Movie not found");
    }
}
