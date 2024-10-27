package com.boekhoud.backendboekhoudapplicatie.presentation.api;

import com.boekhoud.backendboekhoudapplicatie.presentation.dto.AccountantDTO;
import com.boekhoud.backendboekhoudapplicatie.service.implementatie.AccountantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/accountants")
public class AccountantController {

    @Autowired
    private AccountantService accountantService;

    @PostMapping("/add")
    public ResponseEntity<AccountantDTO> createAccountant(@RequestBody AccountantDTO accountantDTO, @RequestParam Long companyId) {
        AccountantDTO newAccountant = accountantService.createAccountant(accountantDTO, companyId);
        return ResponseEntity.ok(newAccountant);
    }

    @GetMapping
    public ResponseEntity<List<AccountantDTO>> getAllAccountants() {
        return ResponseEntity.ok(accountantService.getAllAccountants());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountantDTO> getAccountantById(@PathVariable Long id) {
        Optional<AccountantDTO> accountant = accountantService.getAccountantById(id);
        return accountant.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<AccountantDTO> updateAccountant(@PathVariable Long id, @RequestBody AccountantDTO accountantDTO, @RequestParam Long companyId) {
        AccountantDTO updatedAccountant = accountantService.updateAccountant(id, accountantDTO, companyId);
        return ResponseEntity.ok(updatedAccountant);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAccountant(@PathVariable Long id) {
        accountantService.deleteAccountant(id);
        return ResponseEntity.noContent().build();
    }
}
