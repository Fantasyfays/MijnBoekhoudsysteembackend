package com.boekhoud.backendboekhoudapplicatie.repository;

import com.boekhoud.backendboekhoudapplicatie.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
