package pl.coderslab.vaglogsviewer.repositories;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.coderslab.vaglogsviewer.entities.Role;
import pl.coderslab.vaglogsviewer.entities.User;

import java.util.List;
import javax.transaction.Transactional;

@Repository
@EntityScan(basePackages = "pl.coderslab.vaglogsviewer.entities")
public interface UserRepository extends JpaRepository<User, Long> {
    User findUserByName(String name);

    @Modifying
    @Transactional
    @Query(value = "UPDATE vag_logs_viewer.users SET name = ?1 , email = ?2 , first_name = ?3 , last_name = ?4 , password = ?5 WHERE id = ?6", nativeQuery = true)
    void updateProfile(String userName, String userEmail, String userFirstName, String userLastName, String userPassword, Long id);

    @Modifying
    @Transactional
    @Query(value = "UPDATE vag_logs_viewer.users SET name = ?1 , email = ?2 , password = ?3 , enabled = ?4 , first_name = ?5 , last_name = ?6 WHERE id = ?7", nativeQuery = true)
    void updateUser(String userName, String email, String password, Boolean enabled, String firstName, String lastName, Long id);

    User findUserById(Long id);

    @Query(value = "SELECT * FROM vag_logs_viewer.users JOIN vag_logs_viewer.user_role ON users.id = user_role.user_id", nativeQuery = true)
    List<User> findAllUsers();


}
