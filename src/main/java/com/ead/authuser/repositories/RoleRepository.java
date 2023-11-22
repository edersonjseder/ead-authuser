package com.ead.authuser.repositories;

import com.ead.authuser.enums.Roles;
import com.ead.authuser.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RoleRepository extends JpaRepository<Role, UUID> {
    @Query("select a from Role a where upper(trim(a.roleName)) like %?1%")
    Optional<List<Role>>findAllByRoleName(String name);
    Optional<Role> findByRoleName(Roles name);
}
