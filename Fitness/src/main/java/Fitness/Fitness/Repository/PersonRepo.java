    package Fitness.Fitness.Repository;


    import Fitness.Fitness.Entity.Person;
    import org.springframework.data.jpa.repository.JpaRepository;
    import org.springframework.stereotype.Repository;

    import java.util.Optional;

    @Repository
    public interface PersonRepo extends JpaRepository<Person, Long> {
        Optional<Person> findByUserName(String userName);
    }
