package pl.coderslab.vaglogsviewer.repositories;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.coderslab.vaglogsviewer.entities.Role;

@Repository
@EntityScan(basePackages = "pl.coderslab.vaglogsviewer.entities")
public interface RoleRepository extends JpaRepository<Role, Integer> {
    Role findRoleByName(String name);


}
