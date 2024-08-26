package cinemas.services;

import cinemas.dtos.CityDto;
import cinemas.dtos.TheaterDto;
import cinemas.models.City;
import java.util.List;
import java.util.Map;

public interface CitiesService {
    List<CityDto> getAllCityDtos();
    City getCityById(int id);
    CityDto getCityDtoWithTheatersById(int id);
    List<City> getAllCity();
    Map<Integer, List<TheaterDto>> getTheatersByCity();
}
