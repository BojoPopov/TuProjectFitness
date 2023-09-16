package Fitness.Fitness.Controller;

import Fitness.Fitness.Entity.Person;
import Fitness.Fitness.Service.PersonService;
import Fitness.Fitness.Utilities.CurrentUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class LoginController {

    @Autowired
    private PersonService personService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> request) {
        String userName = request.get("userName");
        String password = request.get("password"); // Include password in the request

        // Check if the username and password match (simulated login)
        Optional<Person> personOptional = personService.getPersonByUsername(userName);
        if (personOptional.isPresent() && personOptional.get().getPassword().equals(password)) {
            Long userId = personOptional.get().getId();
            boolean admin = personOptional.get().isAdmin();
            CurrentUser.getInstance().setUserId(userId);
            CurrentUser.getInstance().setAdmin(admin);


            Map<String, Long> response = Collections.singletonMap("userId", userId);
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Invalid credentials");
        }
    }
}
