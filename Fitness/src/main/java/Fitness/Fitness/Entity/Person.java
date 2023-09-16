package Fitness.Fitness.Entity;


import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Table(name = "person")
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    @Column(unique = true)
    private String userName;
    private String password;
    @Column(unique = true)
    private String email;
    @Column(unique = true)
    private String phoneNumber;
    private int height;
    private double weight;
    private Date dateOfBirth;
    private boolean gender;
    private boolean isAdmin;


}
