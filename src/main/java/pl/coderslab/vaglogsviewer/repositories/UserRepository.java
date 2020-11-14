package pl.coderslab.vaglogsviewer.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.coderslab.vaglogsviewer.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findUserByName(String name);


}
