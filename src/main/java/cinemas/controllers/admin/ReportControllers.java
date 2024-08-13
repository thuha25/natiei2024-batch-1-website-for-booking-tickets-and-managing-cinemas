package cinemas.controllers.admin;

import cinemas.enums.ReportTimeSpanEnum;
import cinemas.models.City;
import cinemas.models.Theater;
import cinemas.services.ReportService;
import cinemas.services.TheaterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import static cinemas.utils.DataTypeUtils.parseIntOrNull;

@Controller
@RequestMapping("/admin/reports")
public class ReportControllers {
    @Autowired
    private ReportService reportService;
    @Autowired
    private TheaterService theaterService;

    @GetMapping("/revenue")
    public String getAdminReport(@RequestParam(value = "theaterId", required = false) String theaterId,
            @RequestParam(value = "time", required = false) String time,
            Model model) {
        // Fetch booking summaries
        Integer id = parseIntOrNull(theaterId);
        ReportTimeSpanEnum timeSpan = ReportTimeSpanEnum.fromOrdinal(parseIntOrNull(time));
        var revenueSingleLineGraphDto = reportService.getSingleLineGraphOfTotalBookingAmount(id,
                timeSpan);

        Theater theater = null;
        City city = null;
        if (id != null) {
            theater = theaterService.getTheaterById(id);
            if (theater == null) {
                throw new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Theater Not Found");
            }
        }
        if (theater != null) {
            city = theater.getCity();
        }
        // Pass data to the Thymeleaf template
        model.addAttribute("city", city);
        model.addAttribute("theater", theater);
        model.addAttribute("timeSpan", timeSpan.ordinal());
        model.addAttribute("revenueLineGraph", revenueSingleLineGraphDto);
        return "admin/revenue-chart-detail";
    }
}
