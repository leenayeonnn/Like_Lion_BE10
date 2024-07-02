package org.example.realprojectexam.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "users")
@Getter@Setter
@ToString
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false , unique = true , length = 50)
    private String username;

    @Column(nullable = false , length = 100)
    private String password;

    @Column(nullable = false , length = 50)
    private String name;

    @Column(nullable = false,unique = true)
    private String email;


    @Column(name = "registration_date" , nullable = false ,updatable = false)
    private LocalDateTime registrationDate = LocalDateTime.now();

    @Column(name="social_id", length = 255)
    private String socialId;

    @Column(name = "provider" , length = 50)
    private String provider;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles;
}
