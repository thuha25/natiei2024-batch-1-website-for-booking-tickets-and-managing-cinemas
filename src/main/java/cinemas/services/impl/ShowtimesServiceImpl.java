package cinemas.services.impl;

import cinemas.dtos.ShowtimeByTheaterDto;
import cinemas.exceptions.CityNotFoundException;
import cinemas.exceptions.MovieNotFoundException;
import cinemas.exceptions.ScreenNotFoundException;
import cinemas.models.Movie;
import cinemas.models.Screen;
import cinemas.models.Showtime;
import cinemas.models.Theater;
import cinemas.repositories.CitiesRepository;
import cinemas.repositories.MoviesRepository;
import cinemas.repositories.ScreensRepository;
import cinemas.repositories.ShowtimesRepository;
import cinemas.services.ShowtimesService;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ShowtimesServiceImpl implements ShowtimesService {
    private ShowtimesRepository showtimesRepository;
    private ScreensRepository screensRepository;
    private CitiesRepository citiesRepository;
    private MoviesRepository moviesRepository;

    public ShowtimesServiceImpl(ShowtimesRepository showtimeRepository,
                                ScreensRepository screensRepository,
                                CitiesRepository citiesRepository,
                                MoviesRepository moviesRepository) {
        this.showtimesRepository = showtimeRepository;
        this.screensRepository = screensRepository;
        this.citiesRepository = citiesRepository;
        this.moviesRepository = moviesRepository;
    }

    @Override
    public Showtime save(Showtime showtime) {
        return showtimesRepository.save(showtime);
    }

    @Override
    public List<Showtime> getShowtimeByDateAndCity(int movieId, int cityId, LocalDate date) {
        return showtimesRepository.getShowtimeByDateAndCity(movieId, cityId, date);
    }

    @Override
    public List<Map<String, String>> generateFormattedDates(LocalDate startDate, int daysRange) {
        DateTimeFormatter monthFormatter = DateTimeFormatter.ofPattern("MM");
        DateTimeFormatter dayOfWeekFormatter = DateTimeFormatter.ofPattern("EEE");
        DateTimeFormatter dayFormatter = DateTimeFormatter.ofPattern("dd");

        return IntStream.range(0, daysRange)
                .mapToObj(startDate::plusDays)
                .map(date -> {
                    Map<String, String> dateMap = new HashMap<>();
                    dateMap.put("month", date.format(monthFormatter));
                    dateMap.put("dayOfWeek", date.format(dayOfWeekFormatter));
                    dateMap.put("day", date.format(dayFormatter));
                    return dateMap;
                })
                .collect(Collectors.toList());
    }

    @Override
    public Map<Theater, Map<Screen, List<Showtime>>> getGroupedShowtimesByTheater(int movieId, int cityId, LocalDate startDate) {
        List<Showtime> showtimes = getShowtimeByDateAndCity(movieId, cityId, startDate);

        // Group showtimes by Screen first
        Map<Screen, List<Showtime>> groupedShowtimes = showtimes.stream()
                .collect(Collectors.groupingBy(Showtime::getScreen));

        // Group screens by Theater
        return groupedShowtimes.entrySet().stream()
                .collect(Collectors.groupingBy(entry -> entry.getKey().getTheater(),
                        Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)));
    }

    @Override
    public Showtime findById(int showtimeId) {
        return showtimesRepository.findById(showtimeId).orElse(null);
    }

    @Override
    public List<ShowtimeByTheaterDto> getShowtimeByTheater(int theaterId, LocalDate date) {
        List<Showtime> showtimes = showtimesRepository.getShowtimeByTheaterAndDate(theaterId, date);
        List<ShowtimeByTheaterDto> showtimeByTheaterDtos = new ArrayList<>();
        for (Showtime showtime : showtimes) {
            Movie movie = showtime.getMovie();
            Screen screen = showtime.getScreen();
            LocalTime timeShow = showtime.getStartTime().toLocalTime();
            String time = timeShow.format(DateTimeFormatter.ofPattern("HH:mm"));
            ShowtimeByTheaterDto showtimeByTheaterDto = new ShowtimeByTheaterDto(showtime.getId(), movie, screen, time);
            showtimeByTheaterDtos.add(showtimeByTheaterDto);
        }
        return showtimeByTheaterDtos;
    }

    @Override
    public Map<Movie, Map<Screen, List<ShowtimeByTheaterDto>>> groupByMovieAndScreen(List<ShowtimeByTheaterDto> showtimeDtos) {
        return showtimeDtos.stream()
                .collect(Collectors.groupingBy(
                        ShowtimeByTheaterDto::getMovie,
                        Collectors.groupingBy(ShowtimeByTheaterDto::getScreen)
                ));
    }

    @Override
    public List<Showtime> getShowtimeByTheaterAndDate(int theaterId, LocalDate date) {
        return showtimesRepository.getShowtimeByTheaterAndDate(theaterId, date);
    }

    @Override
    public List<Showtime> getShowtimeByTheaterAndDateWithStartTimeAsc(int theaterId, LocalDate date) {
        return showtimesRepository.getShowtimeByTheaterAndDateWithStartTimeAsc(theaterId, date);
    }

    @Override
    public Showtime createShowtime(int cityId, int screenId, int movieId, LocalDate date, LocalTime time, int priceStandard, int priceVip) throws ScreenNotFoundException, CityNotFoundException, MovieNotFoundException {
        Showtime showtime = new Showtime();
        var screenOptional = screensRepository.findById(screenId);
        if (screenOptional.isEmpty()) {
            throw new ScreenNotFoundException();
        }
        var cityOptional = citiesRepository.findById(cityId);
        if (cityOptional.isEmpty()) {
            throw new CityNotFoundException();
        }
        var movieOptional = moviesRepository.findById(movieId);
        if (movieOptional.isEmpty()) {
            throw new MovieNotFoundException();
        }
        var screen = screenOptional.get();
        var city = cityOptional.get();
        var movie = movieOptional.get();
        // Get the system's default time zone
        ZoneId systemZoneId = ZoneId.systemDefault();

        // Convert LocalDate and LocalTime to ZonedDateTime using the system's default time zone
        ZonedDateTime startTime = ZonedDateTime.of(date, time, systemZoneId);
        showtime.setScreen(screen);
        showtime.setCity(city);
        showtime.setMovie(movie);
        showtime.setStartTime(startTime);
        showtime.setPriceStandard(priceStandard);
        showtime.setPriceVip(priceVip);
        return showtimesRepository.save(showtime);
    }
}
