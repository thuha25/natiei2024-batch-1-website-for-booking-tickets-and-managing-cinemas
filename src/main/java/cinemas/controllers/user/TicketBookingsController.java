package cinemas.controllers.user;

import cinemas.dtos.FoodSelectionDto;
import cinemas.dtos.FoodSelectionFormDto;
import cinemas.dtos.SeatSelectionDto;
import cinemas.dtos.SeatSelectionFormDto;
import cinemas.exceptions.ShowtimeNotFoundException;
import cinemas.models.*;
import cinemas.services.*;
import cinemas.utils.BookingUtils;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/ticket-bookings")
public class TicketBookingsController {
    @Autowired
    private ShowtimesService showtimesService;
    @Autowired
    private ShowtimeSeatsService showtimeSeatsService;
    @Autowired
    private FoodsService foodsService;
    @Autowired
    private SeatsService seatsService;
    @Autowired
    private UsersService usersService;

    @GetMapping("/showtimes/{id}")
    public String show(@PathVariable("id") Integer showtimeId, Model model) throws ShowtimeNotFoundException {
        Showtime showtime = showtimesService.findById(showtimeId);
        if(showtime==null){
            throw new ShowtimeNotFoundException();
        }
        var seatSelectionForm = new SeatSelectionFormDto();
        var seatGrid = showtimeSeatsService.getSeatsGridByShowtime(showtimeId);
        model.addAttribute("showtime", showtime);
        model.addAttribute("seatGrid", seatGrid);
        model.addAttribute("screenColumns", seatGrid[0].length); // Number of columns
        model.addAttribute("screenRows", seatGrid.length); // Number of rows
        model.addAttribute("seatSelectionForm", seatSelectionForm);
        return "user/showtimes/show";
    }
    @PostMapping("/showtimes/{id}") // view seats of a showtime
    public String showFoods(@PathVariable("id") int id, Model model) {
        return "user/showtimes/show";
    }

}