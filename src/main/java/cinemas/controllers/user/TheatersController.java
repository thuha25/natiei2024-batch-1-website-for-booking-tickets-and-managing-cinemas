package cinemas.controllers.user;

import cinemas.dtos.CityDto;
import cinemas.dtos.ShowtimeByTheaterDto;
import cinemas.dtos.ShowtimeSeatDto;
import cinemas.exceptions.TheaterNotFoundException;
import cinemas.models.Movie;
import cinemas.models.Screen;
import cinemas.models.Theater;
import cinemas.services.CitiesService;
import cinemas.services.ShowtimesService;
import cinemas.services.TheatersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/theaters")
public class TheatersController {
    @Autowired
    private CitiesService citiesService;
    @Autowired
    private ShowtimesService showtimesService;
    @Autowired
    private TheatersService theatersService;
    @GetMapping()
    public String index(Model model) {
        List<CityDto> cities = citiesService.getAllCityDtos();
        model.addAttribute("cities", cities);
        return "user/theaters/index";
    }
    @GetMapping("/detail/{id}")
    public String init(@PathVariable("id") Integer theaterId, Model model) throws TheaterNotFoundException {
        Theater theater = theatersService.getTheaterById(theaterId);
        if(theater == null){
            throw new TheaterNotFoundException();
        }
        LocalDate startDate = LocalDate.now();
        List<Map<String, String>> formattedDates = showtimesService.generateFormattedDates(startDate, 20);
        List<ShowtimeByTheaterDto> showtimes = showtimesService.getShowtimeByTheater(theaterId, startDate);
        Map<Movie, Map<Screen, List<ShowtimeByTheaterDto>>> groupedShowtimeMovie = showtimesService.groupByMovieAndScreen(showtimes);
        model.addAttribute("theater", theater);
        model.addAttribute("formattedDates", formattedDates);
        model.addAttribute("groupedShowtimes", groupedShowtimeMovie);
        return "user/theaters/index :: showtime-detail";
    }

    @GetMapping("/detail/showtime/{id}")
    public String show(@PathVariable("id") Integer theaterId, @RequestParam("date") String dateStr, Model model) throws TheaterNotFoundException {
        Theater theater = theatersService.getTheaterById(theaterId);
        if(theater == null){
            throw new TheaterNotFoundException();
        }
        LocalDate selectedDate = LocalDate.parse(dateStr);
        List<ShowtimeByTheaterDto> showtimes = showtimesService.getShowtimeByTheater(theaterId, selectedDate);
        Map<Movie, Map<Screen, List<ShowtimeByTheaterDto>>> groupedShowtimeMovie = showtimesService.groupByMovieAndScreen(showtimes);
        model.addAttribute("groupedShowtimes", groupedShowtimeMovie);
        return "user/theaters/index :: theater-detail-showtime";
    }
}
