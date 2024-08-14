package cinemas.services.impl;

import cinemas.models.Screen;
import cinemas.repositories.ScreensRepository;
import cinemas.services.ScreensService;

import java.util.Optional;

public class ScreensServiceImpl implements ScreensService {
    private ScreensRepository screensRepository;
    public ScreensServiceImpl(ScreensRepository screenRepository){this.screensRepository = screenRepository;}

    @Override
    public Screen save(Screen screen) {
        return screensRepository.save(screen);
    }

    @Override
    public Optional<Screen> findById(int id) {
        return screensRepository.findById(id);
    }
}
