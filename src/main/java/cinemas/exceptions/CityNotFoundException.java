package cinemas.exceptions;

import javassist.NotFoundException;

public class CityNotFoundException extends NotFoundException {
    public CityNotFoundException() {
        super("City not found");
    }
}
