package com.boekhoud.backendboekhoudapplicatie.dal.repository;

import com.boekhoud.backendboekhoudapplicatie.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {
}
