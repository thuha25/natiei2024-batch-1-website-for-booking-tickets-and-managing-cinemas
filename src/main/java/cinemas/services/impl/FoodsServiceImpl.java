package cinemas.services.impl;

import cinemas.dtos.Pageable;
import cinemas.dtos.PaginationResult;
import cinemas.models.Food;
import cinemas.repositories.FoodsRepository;
import cinemas.services.FoodsService;

import java.util.List;
import java.util.Optional;

public class FoodsServiceImpl implements FoodsService {
    private FoodsRepository foodsRepository;
    public FoodsServiceImpl(FoodsRepository foodsRepository){this.foodsRepository = foodsRepository;}

    @Override
    public List<Food> getAllFoods() {
        return foodsRepository.findAll();
    }
    @Override
    public Optional<Food> findById(int id) {
        return foodsRepository.findById(id);
    }

    @Override
    public Food save(Food food) {
        return foodsRepository.save(food);
    }
    @Override
    public PaginationResult<Food> getPaginationFoodsByName(String name, int page, int size) {
        var pageable = new Pageable(page, size);
        var foods = foodsRepository.getFoodsByName(name, pageable);
        var totalElements = foodsRepository.countFoodsByName(name);
        return new PaginationResult<>(totalElements, size, foods);
    }
}
