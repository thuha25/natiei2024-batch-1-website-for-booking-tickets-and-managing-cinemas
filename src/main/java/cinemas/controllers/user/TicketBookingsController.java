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
        var foodSelectionForm = new FoodSelectionFormDto();
        var seatGrid = showtimeSeatsService.getSeatsGridByShowtime(showtimeId);
        model.addAttribute("showtime", showtime);
        model.addAttribute("seatGrid", seatGrid);
        model.addAttribute("screenColumns", seatGrid[0].length); // Number of columns
        model.addAttribute("screenRows", seatGrid.length); // Number of rows
        model.addAttribute("seatSelectionForm", seatSelectionForm);
        model.addAttribute("foodSelectionForm", foodSelectionForm);
        return "user/showtimes/show";
    }

    @PostMapping("/showtimes/{id}")
    public String showFoods(@RequestParam(value = "seatIds", required = false) Integer[] selectedIds, @PathVariable("id") Integer showtimeId, HttpSession session, Model model) throws ShowtimeNotFoundException {
        Showtime showtime = showtimesService.findById(showtimeId);
        if(showtime==null){
            throw new ShowtimeNotFoundException();
        }
        User user = (User) session.getAttribute("user");
        var showtimeSeats = showtimeSeatsService.createShowtimeSeats(showtimeId, user.getId(), selectedIds);
        var seatSelectionForm = new SeatSelectionFormDto();
        for(var showtimeSeat : showtimeSeats){
            Seat seat = showtimeSeat.getSeat();
            var seatSelection = new SeatSelectionDto();
            seatSelection.setSeat(seat);
            seatSelection.setPrice(BookingUtils.getSeatPrice(seat, showtime));
            seatSelectionForm.addSeatSelection(seatSelection);
        }
        session.setAttribute("seatSelectionForm", seatSelectionForm);
        List<Food> foods = foodsService.getAllFoods();
        var foodSelectionForm = new FoodSelectionFormDto();
        foods.forEach(food -> {
            var foodSelection = new FoodSelectionDto();
            foodSelection.setFood(food);
            foodSelection.setCount(0);
            foodSelectionForm.addFoodSelection(foodSelection);
        });
        model.addAttribute("seatSelectionForm", seatSelectionForm);
        model.addAttribute("foodSelectionForm", foodSelectionForm);
        model.addAttribute("showtime", showtime);
        return "user/foods/index";
    }

}