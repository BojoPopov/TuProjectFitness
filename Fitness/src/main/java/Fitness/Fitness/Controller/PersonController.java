package Fitness.Fitness.Controller;

import Fitness.Fitness.Entity.Person;
import Fitness.Fitness.Service.PersonService;
import Fitness.Fitness.Utilities.CurrentUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/people")
public class PersonController {
    @Autowired
    private PersonService personService;

    @GetMapping("/getPeople")
    public List<Person> getAllPersons() {
        return personService.getAllPersons();
    }

    @GetMapping("/getPerson/{id}")
    public ResponseEntity<Person> getPersonById(@PathVariable Long id) {
        Optional<Person> person = personService.getPersonById(id);
        return person.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                      .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    @GetMapping("/getPerson")
    public ResponseEntity<Person> getPersonById() {
        Optional<Person> person = personService.getPersonById(CurrentUser.getInstance().getUserId());
        return person.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/createPerson")
    public ResponseEntity<Person> createPerson(@RequestBody Person person) {
        Person createdPerson = personService.createPerson(person);
        return new ResponseEntity<>(createdPerson, HttpStatus.CREATED);
    }

    @PatchMapping("/updatePerson")
    public ResponseEntity<Person> updatePerson (@RequestBody Map<String, Object> updates) {
        Optional<Person> optionalPerson = personService.getPersonById(CurrentUser.getInstance().getUserId());

        if (optionalPerson.isPresent()) {
            Person person = optionalPerson.get();

            // Apply partial updates
            if (updates.containsKey("firstName")) {
                person.setFirstName((String) updates.get("firstName"));
            }
            if (updates.containsKey("lastName")) {
                person.setLastName((String) updates.get("lastName"));
            }
            if (updates.containsKey("userName")) {
                person.setUserName((String) updates.get("userName"));
            }
            if (updates.containsKey("password")) {
                person.setFirstName((String) updates.get("password"));
            }
            if (updates.containsKey("email")) {
                person.setLastName((String) updates.get("email"));
            }
            if (updates.containsKey("phoneNumber")) {
                person.setUserName((String) updates.get("phoneNumber"));
            }
            if (updates.containsKey("height")) {
                person.setFirstName((String) updates.get("height"));
            }
            if (updates.containsKey("weight")) {
                person.setLastName((String) updates.get("weight"));
            }

            Person updatedPerson = personService.updatePerson(CurrentUser.getInstance().getUserId(), person);
            return new ResponseEntity<>(updatedPerson, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/deletePerson/{id}")
    public ResponseEntity<Void> deletePerson(@PathVariable Long id) {
        if (CurrentUser.getInstance().isAdmin()){
            personService.deletePerson(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

    }
    @DeleteMapping("/deletePerson")
    public ResponseEntity<Void> deletePerson() {
        personService.deletePerson(CurrentUser.getInstance().getUserId());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
