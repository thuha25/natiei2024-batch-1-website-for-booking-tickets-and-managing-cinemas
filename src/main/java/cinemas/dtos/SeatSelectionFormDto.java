package cinemas.dtos;

import java.util.ArrayList;
import java.util.List;

public class SeatSelectionFormDto {
    private List<SeatSelectionDto> seatSelectionsDto;
    public SeatSelectionFormDto(){
        this.seatSelectionsDto = new ArrayList<>();
    }
    public void addSeatSelection(SeatSelectionDto seatSelectionDto){seatSelectionsDto.add(seatSelectionDto);}
    public List<SeatSelectionDto> getSeatSelectionsDto(){return seatSelectionsDto;}
    public int getTotalPrice(){
        int totalPrice = 0;
        for(var seatSelection : seatSelectionsDto){
            totalPrice += seatSelection.getPrice();
        }
        return totalPrice;
    }
}
