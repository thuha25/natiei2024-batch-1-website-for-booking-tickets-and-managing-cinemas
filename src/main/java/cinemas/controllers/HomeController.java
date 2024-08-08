package cinemas.controllers;

import cinemas.models.Banner;
import cinemas.models.User;
import cinemas.services.BannerService;
import cinemas.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {
    @Autowired
    private UserService userService;

    @Autowired
    private BannerService bannerService;

    @GetMapping("/")
    public String home(Model model) {
        List<Banner> banners = bannerService.getAllBanners();
        model.addAttribute("banners", banners);
        return "user/home-page";
    }

}
