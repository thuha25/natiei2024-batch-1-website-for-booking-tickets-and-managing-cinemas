package cinemas.controllers.user;

import cinemas.dtos.BookingDto;
import cinemas.exceptions.BookingNotFoundException;
import cinemas.models.User;
import cinemas.services.BookingsService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("users/bookings")
public class BookingsController {
    @Autowired
    private BookingsService bookingsService;
    @GetMapping
    public String index(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        List<BookingDto> bookingsDto = bookingsService.getBookingsDto(user.getId());
        model.addAttribute("bookings", bookingsDto);
        model.addAttribute("section", "booking");
        return "user/bookings/index";
    }

    @GetMapping("{id}")
    public String show(@PathVariable("id") Integer bookingId, Model model, HttpSession session) throws BookingNotFoundException {
        User user = (User) session.getAttribute("user");
        BookingDto bookingDto = bookingsService.getBookingDtoById(bookingId, user.getId());
        model.addAttribute("booking", bookingDto);
        model.addAttribute("section", "booking-detail");
        return "user/bookings/show";
    }
}
