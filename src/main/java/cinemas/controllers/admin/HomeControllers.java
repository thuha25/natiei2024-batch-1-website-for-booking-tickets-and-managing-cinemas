package cinemas.controllers.admin;

import cinemas.enums.ReportTimeSpanEnum;
import cinemas.services.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeControllers {
    @Autowired
    private ReportService reportService;

    @GetMapping(value = {"/admin", "/admin/"})
    public String getAdminDashboard(Model model) {
        var revenueSingleLineGraphDto = reportService.getSingleLineGraphOfTotalBookingAmount(
                null,
                ReportTimeSpanEnum.WEEK);

        model.addAttribute("revenueLineGraph", revenueSingleLineGraphDto);
        return "admin/dashboard";
    }
}
