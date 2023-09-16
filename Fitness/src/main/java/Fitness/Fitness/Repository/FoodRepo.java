package Fitness.Fitness.Repository;

import Fitness.Fitness.Entity.Food;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FoodRepo extends JpaRepository<Food, Long> {
    Optional<Food> findByName(String name);

    boolean existsByName(String name);

    void deleteByName(String name);
}
