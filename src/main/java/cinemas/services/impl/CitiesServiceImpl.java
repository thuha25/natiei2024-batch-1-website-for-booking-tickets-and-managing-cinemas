package cinemas.services.impl;

import cinemas.dtos.CityDto;
import cinemas.dtos.TheaterDto;
import cinemas.models.City;
import cinemas.models.Theater;
import cinemas.repositories.CitiesRepository;
import cinemas.repositories.TheatersRepository;
import cinemas.services.CitiesService;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CitiesServiceImpl implements CitiesService {

    private final CitiesRepository citiesRepository;
    private final TheatersRepository theatersRepository;

    public CitiesServiceImpl(CitiesRepository citiesRepository, TheatersRepository theatersRepository) {
        this.citiesRepository = citiesRepository;
        this.theatersRepository = theatersRepository;
    }

    @Override
    public List<CityDto> getAllCityDtos() {
        var cities = citiesRepository.findAll();
        var cityDtos = new ArrayList<CityDto>();
        cities.forEach(city -> {
            cityDtos.add(new CityDto(city.getId(), city.getName(), null));
        });
        return cityDtos;
    }

    @Override
    public City getCityById(int id) {
        return citiesRepository.findById(id).orElse(null);
    }

    @Override
    public CityDto getCityDtoWithTheatersById(int id) {
        var city = citiesRepository.findById(id);
        if (city.isEmpty()) {
            return null;
        }
        var theaters = city.get().getTheaters();
        List<TheaterDto> theaterDtos = new ArrayList<>();
        theaters.forEach(theater ->
                theaterDtos.add(new TheaterDto(theater.getId(), theater.getName(), theater.getLocation()))
        );
        return new CityDto(city.get().getId(), city.get().getName(), theaterDtos);
    }
    @Override
    public List<City> getAllCity() {
        return citiesRepository.findAll();
    }

    @Override
    public Map<Integer, List<TheaterDto>> getTheatersByCity() {
        // Fetch all theaters from the repository
        List<Theater> theaters = theatersRepository.findAll();
        // Group theaters by city
        return theaters.stream()
                .collect(Collectors.groupingBy(
                        theater -> theater.getCity().getId(),
                        Collectors.mapping(
                                theater -> new TheaterDto(theater.getId(), theater.getName()),
                                Collectors.toList()
                        )
                ));
    }
}
