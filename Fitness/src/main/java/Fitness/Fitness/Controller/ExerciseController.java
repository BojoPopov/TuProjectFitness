package Fitness.Fitness.Controller;

import Fitness.Fitness.Entity.Exercise;
import Fitness.Fitness.Entity.Food;
import Fitness.Fitness.Service.ExerciseService;
import Fitness.Fitness.Utilities.CurrentUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
    @RequestMapping("/exercises")
public class ExerciseController {

    @Autowired
    private ExerciseService exerciseService;

    @GetMapping("/getExercises")
    public List<Exercise> getAllExercises() {
        return exerciseService.getAllExercises();
    }

    @GetMapping("/getExercise/{name}")
    public ResponseEntity<Exercise> getExerciseById(@PathVariable String name) {
        Optional<Exercise> exercise = exerciseService.getExerciseByName(name);
        return exercise.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/createExercise")
    public ResponseEntity<Exercise> createExercise(@RequestBody Exercise exercise) {
        if (CurrentUser.getInstance().isAdmin()){
            Exercise createdExercise = exerciseService.createExercise(exercise);
            return new ResponseEntity<>(createdExercise, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

    }

    @PatchMapping("/updateExercise/{name}")
    public ResponseEntity<Exercise> updateExercise (@PathVariable String name, @RequestBody Map<String, Object> updates) {
        if (CurrentUser.getInstance().isAdmin()){
            Optional<Exercise> optionalExercise = exerciseService.getExerciseByName(name);

            if (optionalExercise.isPresent()) {
                Exercise exercise = optionalExercise.get();

                // Apply partial updates
                if (updates.containsKey("name")) {
                    exercise.setName((String) updates.get("name"));
                }
                if (updates.containsKey("caloriesBurnedPerRepetition")) {
                    exercise.setCaloriesBurnedPerRepetition((double) updates.get("caloriesBurnedPerRepetition"));
                }

                Exercise updatedExercise = exerciseService.updateExercise(name, exercise);
                return new ResponseEntity<>(updatedExercise, HttpStatus.OK);
            }

            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

    }
    @DeleteMapping("/deleteExercise/{name}")
    public ResponseEntity<Void> deleteExercise(@PathVariable String name) {
        if (CurrentUser.getInstance().isAdmin()){
            exerciseService.deleteExercise(name);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

    }
}
