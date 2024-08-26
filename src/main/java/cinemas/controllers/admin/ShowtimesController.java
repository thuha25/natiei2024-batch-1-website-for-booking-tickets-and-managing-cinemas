package cinemas.controllers.admin;


import cinemas.dtos.ShowtimeByTheaterDto;
import cinemas.models.Movie;
import cinemas.models.Screen;
import cinemas.services.CitiesService;
import cinemas.services.ShowtimesService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Controller("adminShowtimesController")
@RequestMapping("admin/showtimes")
public class ShowtimesController {

    @Autowired
    private ShowtimesService showtimesService;
    @Autowired
    private CitiesService citiesService;
    @GetMapping
    public String index(@RequestParam(name = "province", required = false) Integer cityId,
                        @RequestParam(name = "theater", required = false) Integer theaterId,
                        @RequestParam(name = "date", required = false) String date,
                        Model model) throws JsonProcessingException {
        Map<Movie, Map<Screen, List<ShowtimeByTheaterDto>>> groupedShowtimes = null;
        if (cityId != null && theaterId != null && date != null) {
            LocalDate selectedDate = LocalDate.parse(date);
            List<ShowtimeByTheaterDto> showtimeDtos = showtimesService.getShowtimeByTheater(theaterId, selectedDate);
            groupedShowtimes = showtimesService.groupByMovieAndScreen(showtimeDtos);
            System.out.println("Showtime Dtos: " + showtimeDtos);
        }
        model.addAttribute("groupedShowtimes", groupedShowtimes);
        model.addAttribute("cities", citiesService.getAllCityDtos());
        model.addAttribute("theatersByCity", new ObjectMapper().writeValueAsString(citiesService.getTheatersByCity()));
        return "admin/showtimes/index";
    }

}