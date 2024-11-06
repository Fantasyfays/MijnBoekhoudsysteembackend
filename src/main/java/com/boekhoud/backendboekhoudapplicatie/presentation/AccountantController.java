package com.boekhoud.backendboekhoudapplicatie.presentation;

import com.boekhoud.backendboekhoudapplicatie.dto.AccountantDTO;
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

    @PostMapping("/add")
    public ResponseEntity<AccountantDTO> createAccountant(@RequestBody AccountantDTO accountantDTO, @RequestParam Long companyId) {
        AccountantDTO newAccountant = accountantService.createAccountant(accountantDTO, companyId);
        return ResponseEntity.ok(newAccountant);
    }

    @GetMapping
    public ResponseEntity<List<AccountantDTO>> getAllAccountants() {
        List<AccountantDTO> accountants = accountantService.getAllAccountants();
        return ResponseEntity.ok(accountants);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountantDTO> getAccountantById(@PathVariable Long id) {
        AccountantDTO accountant = accountantService.getAccountantById(id);
        return ResponseEntity.ok(accountant);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AccountantDTO> updateAccountant(@PathVariable Long id, @RequestBody AccountantDTO accountantDTO) {
        AccountantDTO updatedAccountant = accountantService.updateAccountant(id, accountantDTO);
        return ResponseEntity.ok(updatedAccountant);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAccountant(@PathVariable Long id) {
        accountantService.deleteAccountant(id);
        return ResponseEntity.noContent().build();
    }
}
