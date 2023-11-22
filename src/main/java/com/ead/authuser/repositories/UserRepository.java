package com.ead.authuser.repositories;

import com.ead.authuser.models.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID>, JpaSpecificationExecutor<User> {
    Optional<User> findUserByEmail(String email);

    boolean existsByCpf(String cpf);

    boolean existsByEmail(String email);

    boolean existsByUsername(String cpf);
    // The fetch type for this relationship between user and role is lazy
    // so for this query we use this annotation to specifically get the roles in fetch mode
    @EntityGraph(attributePaths = "roles", type = EntityGraph.EntityGraphType.FETCH)
    Optional<User> findUserByUsername(String username);
    @EntityGraph(attributePaths = "roles", type = EntityGraph.EntityGraphType.FETCH)
    Optional<User> findUserById(UUID id);

    @Query(value = "SELECT constraint_name from information_schema.constraint_column_usage where table_name = 'tb_users_roles' and column_name = 'role_id' and constraint_name <> 'unique_role_user'", nativeQuery = true)
    String getConstraintAccess();
}