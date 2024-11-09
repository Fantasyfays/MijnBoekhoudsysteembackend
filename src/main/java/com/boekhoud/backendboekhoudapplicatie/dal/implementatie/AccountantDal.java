package com.boekhoud.backendboekhoudapplicatie.dal.implementatie;

import com.boekhoud.backendboekhoudapplicatie.dal.entity.Accountant;
import com.boekhoud.backendboekhoudapplicatie.dal.repository.AccountantRepository;
import com.boekhoud.backendboekhoudapplicatie.service.dalinterface.IAccountantDal;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AccountantDal implements IAccountantDal {

    private final AccountantRepository accountantRepository;

    @Override
    public Accountant save(Accountant accountant) {
        return accountantRepository.save(accountant);
    }

    @Override
    public Optional<Accountant> findById(Long id) {
        return accountantRepository.findById(id);
    }

    @Override
    public List<Accountant> findAll() {
        return accountantRepository.findAll();
    }

    @Override
    public void deleteById(Long id) {
        accountantRepository.deleteById(id);
    }
}
