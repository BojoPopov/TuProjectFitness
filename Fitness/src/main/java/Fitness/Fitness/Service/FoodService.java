package Fitness.Fitness.Service;


import Fitness.Fitness.Entity.Food;
import Fitness.Fitness.Repository.FoodRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FoodService {

    @Autowired
    private FoodRepo foodRepo;

    public List<Food> getAllFoods() {
        return foodRepo.findAll();
    }

    public Optional<Food> getFoodByName(String name) {
        return foodRepo.findByName(name);
    }

    public Food createFood(Food food) {
        return foodRepo.save(food);
    }

    public Food updateFood(String name, Food updatedFood) {
        if (foodRepo.existsByName(name)) {
            updatedFood.setId(foodRepo.findByName(name).get().getId());
            return foodRepo.save(updatedFood);
        }
        return null;
    }

    public void deleteFood(String name) {
        foodRepo.delete(foodRepo.findByName(name).get());
    }
}
