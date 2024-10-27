package com.boekhoud.backendboekhoudapplicatie.dal.repository;

import com.boekhoud.backendboekhoudapplicatie.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
