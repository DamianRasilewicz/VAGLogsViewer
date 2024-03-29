package pl.coderslab.vaglogsviewer.services;

import pl.coderslab.vaglogsviewer.entities.Role;

public interface RoleService {

    Role findByRoleName(String name);

    void saveRole(Role role);

    void updateUserRole(int roleId, Integer userId);
}
