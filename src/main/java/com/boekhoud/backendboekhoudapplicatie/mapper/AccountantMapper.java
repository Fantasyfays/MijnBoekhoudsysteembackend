package com.boekhoud.backendboekhoudapplicatie.mapper;

import com.boekhoud.backendboekhoudapplicatie.dal.entity.Accountant;
import com.boekhoud.backendboekhoudapplicatie.dto.AccountantDTO;
import com.boekhoud.backendboekhoudapplicatie.dto.CreateAccountantDTO;
import org.springframework.stereotype.Component;

@Component
public class AccountantMapper {

    public AccountantDTO toDTO(Accountant accountant) {
        return AccountantDTO.builder()
                .id(accountant.getId())
                .firstName(accountant.getFirstName())
                .lastName(accountant.getLastName())
                .email(accountant.getEmail())
                .phoneNumber(accountant.getPhoneNumber())
                .specialization(accountant.getSpecialization())
                .officeLocation(accountant.getOfficeLocation())
                .dateOfHire(accountant.getDateOfHire())
                .activeStatus(accountant.getActiveStatus())
                .build();
    }

    public Accountant toEntity(AccountantDTO accountantDTO) {
        Accountant accountant = new Accountant();
        accountant.setFirstName(accountantDTO.getFirstName());
        accountant.setLastName(accountantDTO.getLastName());
        accountant.setEmail(accountantDTO.getEmail());
        accountant.setPhoneNumber(accountantDTO.getPhoneNumber());
        accountant.setSpecialization(accountantDTO.getSpecialization());
        accountant.setOfficeLocation(accountantDTO.getOfficeLocation());
        accountant.setDateOfHire(accountantDTO.getDateOfHire());
        accountant.setActiveStatus(accountantDTO.getActiveStatus());
        return accountant;
    }

    public Accountant toEntity(CreateAccountantDTO createAccountantDTO) {
        Accountant accountant = new Accountant();
        accountant.setFirstName(createAccountantDTO.getFirstName());
        accountant.setLastName(createAccountantDTO.getLastName());
        accountant.setEmail(createAccountantDTO.getEmail());
        accountant.setPhoneNumber(createAccountantDTO.getPhoneNumber());
        accountant.setSpecialization(createAccountantDTO.getSpecialization());
        accountant.setOfficeLocation(createAccountantDTO.getOfficeLocation());
        accountant.setActiveStatus(createAccountantDTO.getActiveStatus());
        return accountant;
    }
}
