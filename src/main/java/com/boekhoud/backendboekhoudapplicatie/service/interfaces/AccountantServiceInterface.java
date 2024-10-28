package com.boekhoud.backendboekhoudapplicatie.service.interfaces;

import com.boekhoud.backendboekhoudapplicatie.presentation.dto.AccountantDTO;
import java.util.List;
import java.util.Optional;

public interface AccountantServiceInterface {
    AccountantDTO createAccountant(AccountantDTO accountantDTO, Long companyId);
    List<AccountantDTO> getAllAccountants();
    Optional<AccountantDTO> getAccountantById(Long id);
    AccountantDTO updateAccountant(Long id, AccountantDTO accountantDTO, Long companyId);
    void deleteAccountant(Long id);
}
