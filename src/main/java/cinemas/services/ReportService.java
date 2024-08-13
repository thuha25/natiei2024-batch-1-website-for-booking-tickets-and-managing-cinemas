package cinemas.services;

import cinemas.dtos.BookingAmountSummary;
import cinemas.dtos.SingleLineGraphDto;
import cinemas.enums.ReportTimeSpanEnum;

import java.util.List;

public interface ReportService {
    SingleLineGraphDto getSingleLineGraphOfTotalBookingAmount(Integer theaterId, ReportTimeSpanEnum timeSpan);
}
