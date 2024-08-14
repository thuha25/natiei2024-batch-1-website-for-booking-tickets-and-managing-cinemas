package cinemas.services;

import cinemas.models.Screen;

import java.util.Optional;

public interface ScreensService {
    Screen save(Screen screen);
    Optional<Screen> findById(int id);
}
