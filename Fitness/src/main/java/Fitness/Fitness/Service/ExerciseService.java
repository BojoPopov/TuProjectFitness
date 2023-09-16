package Fitness.Fitness.Service;


import Fitness.Fitness.Entity.Exercise;
import Fitness.Fitness.Repository.ExerciseRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExerciseService {

    @Autowired
    private ExerciseRepo exerciseRepo;

    public List<Exercise> getAllExercises() {
        return exerciseRepo.findAll();
    }

    public Optional<Exercise> getExerciseByName(String name) {
        return exerciseRepo.findByName(name);
    }

    public Exercise createExercise(Exercise exercise) {
        return exerciseRepo.save(exercise);
    }

    public Exercise updateExercise(String name, Exercise updatedExercise) {
        if (exerciseRepo.existsByName(name)) {
            updatedExercise.setId(exerciseRepo.findByName(name).get().getId());
            return exerciseRepo.save(updatedExercise);
        }
        return null;
    }

    public void deleteExercise(String name) {
        exerciseRepo.delete(exerciseRepo.findByName(name).get());
    }
}
