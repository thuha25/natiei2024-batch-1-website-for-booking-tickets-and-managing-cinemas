package cinemas.repositories;

import cinemas.dtos.Pageable;
import cinemas.models.Food;

import java.util.List;

public interface FoodsRepository extends BaseRepository<Food, Integer>{
    List<Food> getFoodsByName(String keyword, Pageable pageable);
    Integer countFoodsByName(String name);
}
