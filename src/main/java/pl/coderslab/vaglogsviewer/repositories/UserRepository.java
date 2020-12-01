package pl.coderslab.vaglogsviewer.repositories;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.coderslab.vaglogsviewer.entities.User;

import java.util.List;
import javax.transaction.Transactional;

@Repository
@EntityScan(basePackages = "pl.coderslab.vaglogsviewer.entities")
public interface UserRepository extends JpaRepository<User, Long> {
    User findUserByName(String name);

    @Modifying
    @Transactional
    @Query(value = "UPDATE users SET name = ?1 , email = ?2 , first_name = ?3 , last_name = ?4 , password = ?5 WHERE id = ?6", nativeQuery = true)
    void updateUser(String userName, String userEmail, String userFirstName, String userLastName, String userPassword, Long id);

    User findUserById(Long id);


}
