package cinemas.controllers.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("admin/room")
public class RoomsController {
    @GetMapping
    public String index() {
        return "admin/roomIndex";
    }
    @RequestMapping("/create")
    public String Create() {
        return "admin/roomCreate";
    }
}
