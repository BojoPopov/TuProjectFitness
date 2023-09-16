    package Fitness.Fitness.Repository;

    import Fitness.Fitness.Entity.Day;
    import Fitness.Fitness.Utilities.DayId;
    import org.springframework.data.jpa.repository.JpaRepository;
    import org.springframework.stereotype.Repository;

    import java.time.LocalDate;
    import java.util.Date;
    import java.util.List;
    import java.util.Optional;



    @Repository
    public interface DayRepo extends JpaRepository<Day, DayId> {
        List<Day> findByPersonId(Long personId);

        Day findByDateAndPersonId(LocalDate date, Long userId);

        boolean existsByDateAndPersonId(LocalDate date, Long userId);
    }

