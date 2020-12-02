package pl.coderslab.vaglogsviewer.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.*;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column()
    @NotNull
    @Size(min = 3, message = "Name is too short (minimum is 3 characters)")
    private String name;

    @NotNull
    @Email(message = "Incorrect email")
    private String email;

    @NotNull()
    private String password;
    private Boolean enabled;

    @NotNull()
    @Size(min = 3, message = "First name is too short (minimum is 3 characters)")
    private String firstName;

    @NotNull()
    @Size(min = 3, message = "Last name is too short (minimum is 3 characters)")
    private String lastName;

    @OneToMany(mappedBy = "user")
    private List<File> files = new ArrayList<>();


    @OneToMany()
    @JoinColumn(name = "user_id")
    private List<Car> cars = new ArrayList<>();

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Role role;

    public User(){}

    public User(String name, String email, String password, Boolean enabled, String firstName, String lastName, Role role) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.enabled = enabled;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
    }

    public User(String name, String email, String password, String firstName, String lastName) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
    }
}
