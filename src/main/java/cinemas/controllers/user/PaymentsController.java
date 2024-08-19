package cinemas.controllers.user;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("{user_id}/payments")
public class PaymentsController {
    @GetMapping() // view seats of a showtime
    public String index(@PathVariable("user_id") int user_id) {
        return "user/payments/index";
    }
}
