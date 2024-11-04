package com.boekhoud.backendboekhoudapplicatie.dal.dalimpl;

import com.boekhoud.backendboekhoudapplicatie.service.dalinterface.AccountantDal;
import com.boekhoud.backendboekhoudapplicatie.dal.entity.Accountant;
import com.boekhoud.backendboekhoudapplicatie.dal.repository.AccountantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AccountantDalImpl implements AccountantDal {

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
