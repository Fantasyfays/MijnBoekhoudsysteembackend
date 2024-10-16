package com.boekhoud.backendboekhoudapplicatie.repository;

import com.boekhoud.backendboekhoudapplicatie.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}