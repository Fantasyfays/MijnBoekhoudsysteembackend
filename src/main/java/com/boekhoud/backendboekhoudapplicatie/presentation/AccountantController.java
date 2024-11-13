package com.boekhoud.backendboekhoudapplicatie.presentation;

import com.boekhoud.backendboekhoudapplicatie.dto.AccountantDTO;
import com.boekhoud.backendboekhoudapplicatie.dto.CreateAccountantDTO;
import com.boekhoud.backendboekhoudapplicatie.dto.UpdateAccountantDTO;
import com.boekhoud.backendboekhoudapplicatie.service.AccountantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/accountants")
public class AccountantController {

    private final AccountantService accountantService;

    @Autowired
    public AccountantController(AccountantService accountantService) {
        this.accountantService = accountantService;
    }

    @PostMapping
    public ResponseEntity<AccountantDTO> createAccountant(
            @RequestBody CreateAccountantDTO createAccountantDTO,
            @RequestParam("companyId") Long companyId) {  // Require companyId as query parameter
        AccountantDTO newAccountant = accountantService.createAccountantWithinCompany(createAccountantDTO, companyId);
        return ResponseEntity.status(201).body(newAccountant);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AccountantDTO> updateAccountant(@PathVariable Long id, @RequestBody UpdateAccountantDTO updateAccountantDTO) {
        AccountantDTO updatedAccountant = accountantService.updateAccountant(id, updateAccountantDTO);
        return ResponseEntity.ok(updatedAccountant);
    }

    @GetMapping
    public ResponseEntity<List<AccountantDTO>> getAllAccountants() {
        return ResponseEntity.ok(accountantService.getAllAccountants());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountantDTO> getAccountantById(@PathVariable Long id) {
        return ResponseEntity.ok(accountantService.getAccountantById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAccountant(@PathVariable Long id) {
        accountantService.deleteAccountant(id);
        return ResponseEntity.noContent().build();
    }
}
