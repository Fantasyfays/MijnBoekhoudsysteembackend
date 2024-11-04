package com.boekhoud.backendboekhoudapplicatie.service.dalinterface;

import com.boekhoud.backendboekhoudapplicatie.dal.entity.Company;

import java.util.List;
import java.util.Optional;

public interface CompanyDal {
    Company save(Company company);

    List<Company> findAll();

    Optional<Company> findById(Long id);

    void deleteById(Long id);
}
