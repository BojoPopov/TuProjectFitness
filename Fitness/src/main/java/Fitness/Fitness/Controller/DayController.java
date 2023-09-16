package Fitness.Fitness.Controller;

import Fitness.Fitness.Entity.Day;
import Fitness.Fitness.Entity.Exercise;
import Fitness.Fitness.Entity.Food;
import Fitness.Fitness.Service.DayService;
import Fitness.Fitness.Service.ExerciseService;
import Fitness.Fitness.Service.FoodService;
import Fitness.Fitness.Utilities.CurrentUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

@RestController
@RequestMapping("/days")
public class DayController {

    @Autowired
    private DayService dayService;
    private ExerciseService exerciseService;
    private FoodService foodService;
    @Autowired
    public DayController(DayService dayService, ExerciseService exerciseService, FoodService foodService) {
        this.dayService = dayService;
        this.exerciseService = exerciseService;
        this.foodService = foodService;

    }


    @GetMapping("/getDays")
    public List<Day> getAllDays() {
        return dayService.getAllDays();
    }
    @GetMapping("/getDaysForPerson")
    public ResponseEntity<List<Day>> getDaysForPerson() {
        List<Day> daysForPerson = dayService.getDaysForPerson(CurrentUser.getInstance().getUserId());
        return new ResponseEntity<>(daysForPerson, HttpStatus.OK);
    }

    @GetMapping("/getDay/{date}")
    public ResponseEntity<Day> getDayByDateAndPersonId(
            @PathVariable("date") String date) throws ParseException {
        String pattern = "yyyy-MM-dd";
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        LocalDate formattedDate = LocalDate.parse(date);
        Optional<Day> day = dayService.getDayByDateAndPersonId(formattedDate, CurrentUser.getInstance().getUserId());
        return day.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

        @PostMapping("/createDay")
        public ResponseEntity<Day> createDay(@RequestBody Day day) {
            Long personId = CurrentUser.getInstance().getUserId();
//            Calendar calendar = Calendar.getInstance();
//            calendar.set(Calendar.HOUR_OF_DAY, 0);
//            calendar.set(Calendar.MINUTE, 0);
//            calendar.set(Calendar.SECOND, 0);
//            calendar.set(Calendar.MILLISECOND, 0);
            day.setDate(LocalDate.now()); // Set the modified date
            Optional<Day> existingDay = dayService.getDayByDateAndPersonId(LocalDate.now(), personId);

            if (existingDay.isPresent()) {
                // A day for this user and date already exists, return an error or update it as needed.
                return new ResponseEntity<>(HttpStatus.CONFLICT); // You can choose an appropriate HTTP status code
            }

            day.setPersonId(personId);
            // Retrieve and associate Foods
            if (day.getFoods() != null) {
                List<Food> associatedFoods = new ArrayList<>();
                for (Food food : day.getFoods()) {
                    Optional<Food> retrievedFood = foodService.getFoodByName(food.getName());
                    if (retrievedFood.isPresent()) {
                        associatedFoods.add(retrievedFood.get());
                    } else {
                        // Handle the case where the Food with the given name does not exist
                        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                    }
                }
                day.setFoods(associatedFoods);
            }

            // Retrieve and associate Exercises
            if (day.getExercises() != null) {
                List<Exercise> associatedExercises = new ArrayList<>();
                for (Exercise exercise : day.getExercises()) {
                    Optional<Exercise> retrievedExercise = exerciseService.getExerciseByName(exercise.getName());
                    if (retrievedExercise.isPresent()) {
                        associatedExercises.add(retrievedExercise.get());
                    } else {
                        // Handle the case where the Exercise with the given name does not exist
                        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                    }
                }
                day.setExercises(associatedExercises);
            }

            // Create the Day entity with the updated fields and entries
            Day createdDay = dayService.createDay(day);
            return new ResponseEntity<>(createdDay, HttpStatus.CREATED);
        }





    @PutMapping("/updateDay/{date}")
    public ResponseEntity<Day> updateDay( @PathVariable("date") String date, @RequestBody Day day) {
        Day updatedDay = dayService.updateDay(LocalDate.parse(date), day);
        return new ResponseEntity<>(updatedDay, HttpStatus.OK);
    }

    @DeleteMapping("/deleteDay/{date}")
    public ResponseEntity<Void> deleteDay(
            @PathVariable("date") String date) throws ParseException {
        String pattern = "yyyy-MM-dd";
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        LocalDate formattedDate = LocalDate.parse(date);
        dayService.deleteDay(formattedDate, CurrentUser.getInstance().getUserId());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}