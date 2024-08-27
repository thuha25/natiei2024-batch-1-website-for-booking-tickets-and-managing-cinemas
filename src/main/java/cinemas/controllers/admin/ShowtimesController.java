package cinemas.controllers.admin;


import cinemas.dtos.ShowtimeByTheaterDto;
import cinemas.models.Movie;
import cinemas.models.Screen;
import cinemas.models.Showtime;
import cinemas.services.CitiesService;
import cinemas.services.ShowtimesService;
import cinemas.services.TheatersService;
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
    @Autowired
    private TheatersService theatersService;
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
}
