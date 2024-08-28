package cinemas.services.impl;

import cinemas.dtos.ScreenDto;
import cinemas.dtos.TheaterDto;
import cinemas.models.Theater;
import cinemas.repositories.TheatersRepository;
import cinemas.services.TheatersService;

import java.util.Set;
import java.util.stream.Collectors;

public class TheatersServiceImpl implements TheatersService {
    private TheatersRepository theatersRepository;

    public TheatersServiceImpl(TheatersRepository theatersRepository) {
        this.theatersRepository = theatersRepository;
    }

    @Override
    public Theater getTheaterById(int id) {
        return theatersRepository.findById(id).orElse(null);
    }

    @Override
    public Theater save(Theater theater) {
        return theatersRepository.save(theater);
    }

    @Override
    public TheaterDto getTheaterDtoById(int id) {
        var theaterOptional = theatersRepository.findById(id);
        if (theaterOptional.isEmpty()) {
            return null;
        }
        var theater = theaterOptional.get();
        var theaterDto = new TheaterDto();
        theaterDto.setId(theater.getId());
        theaterDto.setName(theater.getName());
        theaterDto.setLocation(theater.getLocation());
        theaterDto.setScreens(theater.getScreens().stream().map(screen -> {
            var screenDto = new ScreenDto();
            screenDto.setId(screen.getId());
            screenDto.setName(screen.getName());
            return screenDto;
        }).collect(Collectors.toList()));
        return theaterDto;
    }
}
