package cinemas.dtos;

import java.util.ArrayList;
import java.util.List;

public class FoodSelectionFormDto {
    private List<FoodSelectionDto> foodSelections;

    public FoodSelectionFormDto() {
        this.foodSelections = new ArrayList<>();
    }

    public void addFoodSelection(FoodSelectionDto foodSelection) {
        foodSelections.add(foodSelection);
    }

    public List<FoodSelectionDto> getFoodSelections() {
        return foodSelections;
    }

    public int getTotalPrice(){
        int totalPrice = 0;
        for(var foodSelection : foodSelections){
            totalPrice += foodSelection.getFood().getPrice() * foodSelection.getCount();
        }
        return totalPrice;
    }

    public List<FoodTotalPriceDto> getFoodTotalPrices(){
        List<FoodTotalPriceDto> foodTotalPricesDto = new ArrayList<>();
        for(var foodSelection : foodSelections){
            if(foodSelection.getCount() > 0){
                FoodTotalPriceDto foodTotalPriceDto = new FoodTotalPriceDto();
                foodTotalPriceDto.setTotalPrice(foodSelection.getFood().getPrice() * foodSelection.getCount());
                foodTotalPriceDto.setName(foodSelection.getFood().getName());
                foodTotalPricesDto.add(foodTotalPriceDto);
            }
        }
        return foodTotalPricesDto;
    }
}
