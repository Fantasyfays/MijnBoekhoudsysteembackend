package com.boekhoud.backendboekhoudapplicatie.service.dalinterface;

import com.boekhoud.backendboekhoudapplicatie.dal.entity.Accountant;

import java.util.List;
import java.util.Optional;

public interface IAccountantDal {
    Accountant save(Accountant accountant);
    Optional<Accountant> findById(Long id);
    List<Accountant> findAll();
    void deleteById(Long id);
}
