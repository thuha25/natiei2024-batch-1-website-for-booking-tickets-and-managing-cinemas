package cinemas.controllers.user;

import cinemas.dtos.BookingCancelFormDto;
import cinemas.dtos.BookingDto;
import cinemas.dtos.PaginationResult;
import cinemas.exceptions.BookingNotFoundException;
import cinemas.models.User;
import cinemas.services.BookingsService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("users/bookings")
public class BookingsController {
    @Autowired
    private BookingsService bookingsService;
    @GetMapping
    public String index(@RequestParam(value = "page", defaultValue = "1") int page, @RequestParam(value = "size", defaultValue = "5") int size,  Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        PaginationResult<BookingDto> bookingsPagination = bookingsService.getPagnitionValidBookingsDtoOfUser(user.getId(), page, size);
        model.addAttribute("bookingsPagination", bookingsPagination);
        model.addAttribute("currentPage", page);
        model.addAttribute("section", "booking");
        return "user/bookings/index";
    }

    @GetMapping("{id}")
    public String show(@PathVariable("id") Integer bookingId, Model model, HttpSession session) throws BookingNotFoundException {
        User user = (User) session.getAttribute("user");
        BookingDto bookingDto = bookingsService.getBookingDtoById(bookingId,user.getId());
        model.addAttribute("booking", bookingDto);
        model.addAttribute("section", "booking-detail");
        model.addAttribute("bookingCancelFormDto", new BookingCancelFormDto());
        return "user/bookings/show";
    }

    @PostMapping("/cancel")
    public String cancel(@ModelAttribute("bookingCancelFormDto") BookingCancelFormDto bookingCancelFormDto, HttpSession session) throws BookingNotFoundException {
        User user = (User) session.getAttribute("user");
        bookingsService.createBookingCancel(user.getId(), bookingCancelFormDto.getBookingId(), bookingCancelFormDto.getReason());
        return "redirect:/users/bookings";
    }
}
