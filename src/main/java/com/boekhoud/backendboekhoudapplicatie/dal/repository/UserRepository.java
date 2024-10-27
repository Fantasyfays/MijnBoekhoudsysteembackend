package com.boekhoud.backendboekhoudapplicatie.dal.repository;

import com.boekhoud.backendboekhoudapplicatie.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
