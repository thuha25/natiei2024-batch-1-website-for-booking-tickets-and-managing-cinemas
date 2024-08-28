package cinemas.exceptions;

import javassist.NotFoundException;

public class ScreenNotFoundException extends NotFoundException {
    public ScreenNotFoundException() {
        super("Screen not found");
    }
}
