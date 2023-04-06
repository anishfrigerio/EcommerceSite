package it.attf.ecommerceSite.repository;

import it.attf.ecommerceSite.enums.RoleEnum;
import it.attf.ecommerceSite.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepo extends JpaRepository<Role, Integer> {
    Optional<Role> findByName(RoleEnum name);
}
