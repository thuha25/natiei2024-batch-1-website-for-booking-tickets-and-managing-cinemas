package cinemas.controllers;

import cinemas.models.Banner;
import cinemas.models.Movie;
import cinemas.models.User;
import cinemas.services.BannerService;
import cinemas.services.MovieService;
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

    @Autowired
    private MovieService movieService;

    @GetMapping("/")
    public String home(Model model) {
        List<Banner> banners = bannerService.getAllBanners();
        List<Movie> movies = movieService.getAllMovies();
        model.addAttribute("banners", banners);
        model.addAttribute("movies", movies);
        return "user/home-page";
    }

}
