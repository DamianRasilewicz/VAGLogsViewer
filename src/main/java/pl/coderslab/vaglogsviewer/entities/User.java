package pl.coderslab.vaglogsviewer.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
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

    @Column(nullable = false)
    private String name;
    private String email;
    private String password;
    private Boolean enabled;

    @Column
    private String firstName;
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
