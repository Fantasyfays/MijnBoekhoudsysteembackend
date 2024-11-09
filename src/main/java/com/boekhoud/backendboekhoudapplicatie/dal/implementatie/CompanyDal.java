package com.boekhoud.backendboekhoudapplicatie.dal.implementatie;

import com.boekhoud.backendboekhoudapplicatie.dal.entity.Company;
import com.boekhoud.backendboekhoudapplicatie.dal.repository.CompanyRepository;
import com.boekhoud.backendboekhoudapplicatie.service.dalinterface.ICompanyDal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class CompanyDal implements ICompanyDal {

    private final CompanyRepository companyRepository;

    @Autowired
    public CompanyDal(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    @Override
    public Company save(Company company) {
        return companyRepository.save(company);
    }

    @Override
    public List<Company> findAll() {
        return companyRepository.findAll();
    }

    @Override
    public Optional<Company> findById(Long id) {
        return companyRepository.findById(id);
    }

    @Override
    public void deleteById(Long id) {
        companyRepository.deleteById(id);
    }
}
