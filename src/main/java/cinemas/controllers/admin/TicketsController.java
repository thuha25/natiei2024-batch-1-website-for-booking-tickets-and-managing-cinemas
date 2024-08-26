package cinemas.controllers.admin;

import cinemas.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller("adminTicketsController")
@RequestMapping("admin/tickets")
public class TicketsController {
    @Autowired
    private BookingsService bookingsService;
    @GetMapping
    public String index(@RequestParam(name = "keyword", defaultValue = "") String keyword,
                        @RequestParam(name = "page", defaultValue = "1") int page,
                        @RequestParam(name = "size", defaultValue = "10") int size,
                        Model model) {
        var bookingsPagination = bookingsService.getPaginationBookingsByIdOrCustomerName(keyword, page, size);
        model.addAttribute("keyword", keyword);
        model.addAttribute("size", size);
        model.addAttribute("currentPage", page);
        model.addAttribute("bookingsPagination", bookingsPagination);
        return "admin/tickets/index";
    }
}
