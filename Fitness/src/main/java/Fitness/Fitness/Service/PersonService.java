package Fitness.Fitness.Service;

import Fitness.Fitness.Entity.Person;
import Fitness.Fitness.Repository.PersonRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PersonService {
    @Autowired
    private PersonRepo personRepo;

    public List<Person> getAllPersons() {
        return personRepo.findAll();
    }

    public Optional<Person> getPersonById(Long id) {
        return personRepo.findById(id);
    }
    public Optional<Person> getPersonByUsername(String userName) {
        return personRepo.findByUserName(userName);
    }

    public Person createPerson(Person person) {
        return personRepo.save(person);
    }

    public Person updatePerson(Long id, Person updatedPerson) {
        if (personRepo.existsById(id)) {
            updatedPerson.setId(id);
            return personRepo.save(updatedPerson);
        }
        return null;
    }

    public void deletePerson(Long id) {
        personRepo.deleteById(id);
    }
}
