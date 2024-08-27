package cinemas.services;

import cinemas.dtos.PaginationResult;
import cinemas.models.Food;

import java.util.List;
import java.util.Optional;

public interface FoodsService {
    List<Food> getAllFoods();
    PaginationResult<Food> getPaginationFoodsByName(String name, int page, int size);
    Optional<Food> findById(int id);
    Food save(Food food);
}
