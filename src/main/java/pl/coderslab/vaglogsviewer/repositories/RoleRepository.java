package pl.coderslab.vaglogsviewer.repositories;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.coderslab.vaglogsviewer.entities.Role;

import javax.transaction.Transactional;

@Repository
@EntityScan(basePackages = "pl.coderslab.vaglogsviewer.entities")
public interface RoleRepository extends JpaRepository<Role, Integer> {
    Role findRoleByName(String name);

    @Modifying
    @Transactional
    @Query(value = "UPDATE vag_logs_viewer.user_role SET role_id = ?1 WHERE user_id = ?2", nativeQuery = true)
    void updateUserRole(int roleId, Long userId);

}
