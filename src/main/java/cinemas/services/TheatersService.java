package cinemas.services;

import cinemas.dtos.TheaterDto;
import cinemas.models.Theater;

public interface TheatersService {
    Theater getTheaterById(int id);
    Theater save(Theater theater);
    TheaterDto getTheaterDtoById(int id);
}
