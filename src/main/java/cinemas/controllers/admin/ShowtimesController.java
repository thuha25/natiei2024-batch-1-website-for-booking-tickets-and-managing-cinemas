package cinemas.controllers.admin;

import cinemas.dtos.ShowtimeCreateFormDto;
import cinemas.enums.MovieStatus;
import cinemas.exceptions.CityNotFoundException;
import cinemas.exceptions.MovieNotFoundException;
import cinemas.exceptions.ScreenNotFoundException;
import cinemas.models.Showtime;
import cinemas.services.CitiesService;
import cinemas.services.MoviesService;
import cinemas.services.ShowtimesService;
import cinemas.services.TheatersService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller("adminShowtimesController")
@RequestMapping("admin/showtimes")
public class ShowtimesController {

    @Autowired
    private ShowtimesService showtimesService;
    @Autowired
    private CitiesService citiesService;
    @Autowired
    private TheatersService theatersService;
    @Autowired
    private MoviesService moviesService;

    @GetMapping
    public String index(@RequestParam(name = "province", required = false) Integer cityId,
                        @RequestParam(name = "theater", required = false) Integer theaterId,
                        @RequestParam(name = "date", required = false) String date,
                        Model model) throws JsonProcessingException {
        List<Showtime> showtimes = null;
        if (cityId != null && theaterId != null && date != null) {
            LocalDate selectedDate = LocalDate.parse(date);
            showtimes = showtimesService.getShowtimeByTheaterAndDateWithStartTimeAsc(theaterId, selectedDate);
        }
        if (cityId != null)
            model.addAttribute("province", citiesService.getCityById(cityId).getName());
        if (theaterId != null)
            model.addAttribute("theater", theatersService.getTheaterById(theaterId).getName());
        if (date != null)
            model.addAttribute("selectedDate", date);
        model.addAttribute("showtimes", showtimes);
        model.addAttribute("cities", citiesService.getAllCityDtos());
        return "admin/showtimes/index";
    }

    @GetMapping("/new")
    public String createShowtime(Model model) {
        model.addAttribute("cities", citiesService.getAllCityDtos());
        model.addAttribute("movies", moviesService.getMoviesByStatus(MovieStatus.NOW_SHOWING));
        model.addAttribute("showtimeCreateFormDto", new ShowtimeCreateFormDto());
        return "admin/showtimes/new";
    }

    @PostMapping
    public String createShowtime(@ModelAttribute("showtimeCreateFormDto") ShowtimeCreateFormDto showtimeCreateFormDto) throws ScreenNotFoundException, MovieNotFoundException, CityNotFoundException {
        showtimesService.createShowtime(showtimeCreateFormDto.getCityId(),
                showtimeCreateFormDto.getScreenId(),
                showtimeCreateFormDto.getMovieId(),
                showtimeCreateFormDto.getDate(),
                showtimeCreateFormDto.getTime(),
                showtimeCreateFormDto.getPriceStandard(),
                showtimeCreateFormDto.getPriceVip());
        return "redirect:/admin/showtimes";
    }
}
