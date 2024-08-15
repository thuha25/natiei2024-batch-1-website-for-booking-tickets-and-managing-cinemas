package cinemas.controllers.admin;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/movies")
public class MoviesController {
    @GetMapping
    public String index() {
        return "admin/movies/index";
    }
}
