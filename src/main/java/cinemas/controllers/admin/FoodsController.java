package cinemas.controllers.admin;

import cinemas.services.FoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/admin/foods")
public class FoodsController {
    @Autowired
    private FoodsService foodsService;
    @GetMapping
    public String index(@RequestParam(name = "keyword", defaultValue = "") String keyword,
                        @RequestParam(name = "page", defaultValue = "1") int page,
                        @RequestParam(name = "size", defaultValue = "10") int size,
                        Model model) {
        var foodsPagination = foodsService.getPaginationFoodsByName(keyword, page, size);
        model.addAttribute("keyword", keyword);
        model.addAttribute("size", size);
        model.addAttribute("currentPage", page);
        model.addAttribute("foodsPagination", foodsPagination);
        return "admin/foods/index";
    }
}
