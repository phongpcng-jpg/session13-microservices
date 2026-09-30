package io.edu.rikkei.identity_service.models.repositories;

import io.edu.rikkei.identity_service.constants.RoleName;
import io.edu.rikkei.identity_service.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role,Long> {
    Optional<Role> findByRoleName(RoleName roleName);
}
