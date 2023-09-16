package Fitness.Fitness.Controller;

import Fitness.Fitness.Entity.Food;
import Fitness.Fitness.Entity.Person;
import Fitness.Fitness.Service.FoodService;
import Fitness.Fitness.Utilities.CurrentUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/foods")
public class FoodController {

    @Autowired
    private FoodService foodService;

    @GetMapping("/getFoods")
    public List<Food> getAllFoods() {
        return foodService.getAllFoods();
    }

    @GetMapping("/getFood/{name}")
    public ResponseEntity<Food> getFoodById(@PathVariable String name) {
        Optional<Food> food = foodService.getFoodByName(name);
        return food.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/createFood")
    public ResponseEntity<Food> createFood(@RequestBody Food food) {
        if (CurrentUser.getInstance().isAdmin()){
            Food createdFood = foodService.createFood(food);
            return new ResponseEntity<>(createdFood, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);


    }

    @PatchMapping("/updateFood/{name}")
    public ResponseEntity<Food> updateFood (@PathVariable String name, @RequestBody Map<String, Object> updates) {
        if (CurrentUser.getInstance().isAdmin()){
            Optional<Food> optionalFood = foodService.getFoodByName(name);

            if (optionalFood.isPresent()) {
                Food food = optionalFood.get();

                // Apply partial updates
                if (updates.containsKey("name")) {
                    food.setName((String) updates.get("name"));
                }
                if (updates.containsKey("caloriesPer100Grams")) {
                    food.setCaloriesPer100Grams((int) updates.get("caloriesPer100Grams"));
                }
                if (updates.containsKey("fatsPer100Grams")) {
                    food.setFatsPer100Grams((double) updates.get("fatsPer100Grams"));
                }
                if (updates.containsKey("proteinPer100Grams")) {
                    food.setProteinPer100Grams((double) updates.get("proteinPer100Grams"));
                }
                if (updates.containsKey("carbohydratesPer100Grams")) {
                    food.setCarbohydratesPer100Grams((double) updates.get("carbohydratesPer100Grams"));
                }

                Food updatedFood = foodService.updateFood(name, food);
                return new ResponseEntity<>(updatedFood, HttpStatus.OK);
            }

            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

    }

    @DeleteMapping("/deleteFood/{name}")
    public ResponseEntity<Void> deleteFood(@PathVariable String name) {
        if (CurrentUser.getInstance().isAdmin()){
            foodService.deleteFood(name);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

    }
}
