package pl.coderslab.vaglogsviewer.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@AllArgsConstructor
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

    @OneToMany()
    @JoinColumn(name = "user_id")
    private List<File> files = new ArrayList<>();

    @OneToMany()
    @JoinColumn(name = "user_id")
    private List<Car> cars = new ArrayList<>();

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> role;

    public User(){}
}
