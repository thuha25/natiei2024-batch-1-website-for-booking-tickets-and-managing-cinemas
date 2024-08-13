package cinemas.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class DatVeController {
    @GetMapping("/datve")
    public String getDatVe() {
        return "user/datve";
    }
    @RequestMapping("/popcornDrinks")
    public String getDatVeCombo() {return "user/popcornDrinks";}
}
