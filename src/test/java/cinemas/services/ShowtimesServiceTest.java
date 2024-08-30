package cinemas.services;

import cinemas.exceptions.CityNotFoundException;
import cinemas.exceptions.MovieNotFoundException;
import cinemas.exceptions.ScreenNotFoundException;
import cinemas.models.City;
import cinemas.models.Movie;
import cinemas.models.Screen;
import cinemas.models.Showtime;
import cinemas.repositories.CitiesRepository;
import cinemas.repositories.MoviesRepository;
import cinemas.repositories.ScreensRepository;
import cinemas.repositories.ShowtimesRepository;
import cinemas.services.impl.ShowtimesServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ShowtimesServiceTest {
    @InjectMocks
    private ShowtimesServiceImpl showtimesService;
    @Mock
    private ScreensRepository screensRepository;
    @Mock
    private ShowtimesRepository showtimesRepository;
    @Mock
    private CitiesRepository citiesRepository;
    @Mock
    private MoviesRepository moviesRepository;

    @BeforeEach
    public void setUp() {
    }

    @Test
    public void testCreateShowtime_ShouldSuccess() throws ScreenNotFoundException, CityNotFoundException, MovieNotFoundException {
        // Arrange
        int cityId = 1;
        int screenId = 1;
        int movieId = 1;
        LocalDate date = LocalDate.of(2024, 8, 29);
        LocalTime time = LocalTime.of(18, 0);
        int priceStandard = 10;
        int priceVip = 20;

        Screen screen = new Screen();
        City city = new City();
        Movie movie = new Movie();

        when(screensRepository.findById(screenId)).thenReturn(Optional.of(screen));
        when(citiesRepository.findById(cityId)).thenReturn(Optional.of(city));
        when(moviesRepository.findById(movieId)).thenReturn(Optional.of(movie));
        when(showtimesRepository.save(any(Showtime.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        Showtime result = showtimesService.createShowtime(cityId, screenId, movieId, date, time, priceStandard, priceVip);

        // Assert
        assertEquals(screen, result.getScreen());
        assertEquals(city, result.getCity());
        assertEquals(movie, result.getMovie());
        assertEquals(ZonedDateTime.of(date, time, ZoneId.systemDefault()), result.getStartTime());
        assertEquals(priceStandard, result.getPriceStandard());
        assertEquals(priceVip, result.getPriceVip());
        verify(showtimesRepository).save(result);
    }

    @Test
    public void testCreateShowtime_ShouldThrowScreenNotFoundException() {
        // Arrange
        int cityId = 1;
        int screenId = 1;
        int movieId = 1;
        LocalDate date = LocalDate.of(2024, 8, 29);
        LocalTime time = LocalTime.of(18, 0);

        when(screensRepository.findById(screenId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ScreenNotFoundException.class, () -> {
            showtimesService.createShowtime(cityId, screenId, movieId, date, time, 10, 20);
        });
    }

    @Test
    public void testCreateShowtime_ShouldThrowCityNotFoundException() {
        // Arrange
        int cityId = 1;
        int screenId = 1;
        int movieId = 1;
        LocalDate date = LocalDate.of(2024, 8, 29);
        LocalTime time = LocalTime.of(18, 0);

        Screen screen = new Screen();
        when(screensRepository.findById(screenId)).thenReturn(Optional.of(screen));
        when(citiesRepository.findById(cityId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(CityNotFoundException.class, () -> {
            showtimesService.createShowtime(cityId, screenId, movieId, date, time, 10, 20);
        });
    }

    @Test
    public void testCreateShowtime_ShouldThrowMovieNotFoundException() {
        // Arrange
        int cityId = 1;
        int screenId = 1;
        int movieId = 1;
        LocalDate date = LocalDate.of(2024, 8, 29);
        LocalTime time = LocalTime.of(18, 0);

        Screen screen = new Screen();
        City city = new City();
        when(screensRepository.findById(screenId)).thenReturn(Optional.of(screen));
        when(citiesRepository.findById(cityId)).thenReturn(Optional.of(city));
        when(moviesRepository.findById(movieId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(MovieNotFoundException.class, () -> {
            showtimesService.createShowtime(cityId, screenId, movieId, date, time, 10, 20);
        });
    }
}
