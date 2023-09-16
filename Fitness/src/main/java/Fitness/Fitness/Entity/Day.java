package Fitness.Fitness.Entity;


import Fitness.Fitness.Utilities.DayId;
import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data
@RequiredArgsConstructor
@IdClass(DayId.class)
@Table(name = "days")
public class Day {

    @Id
    private LocalDate date;

    @Id
    private Long personId;

    @ManyToMany
    private List<Food> foods = new ArrayList<>();
    private List<Integer> grams = new ArrayList<>();


    @ManyToMany(cascade=CascadeType.ALL)
    private List<Exercise> exercises = new ArrayList<>();
    private List<Integer> sets = new ArrayList<>();
    private List<List<Integer>> repetitions = new ArrayList<>();

    private int stepsTaken;

    private double weight;
}

