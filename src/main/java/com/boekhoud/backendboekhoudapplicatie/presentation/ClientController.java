package com.boekhoud.backendboekhoudapplicatie.presentation;

import com.boekhoud.backendboekhoudapplicatie.dto.ClientDTO;
import com.boekhoud.backendboekhoudapplicatie.dto.CreateClientDTO;
import com.boekhoud.backendboekhoudapplicatie.dto.UpdateClientDTO;
import com.boekhoud.backendboekhoudapplicatie.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/v1/clients")
public class ClientController {

    private final ClientService clientService;

    @Autowired
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping
    public ResponseEntity<ClientDTO> createClient(
            @RequestBody CreateClientDTO createClientDTO,
            @RequestParam("companyId") Long companyId) {
        ClientDTO newClient = clientService.createClientWithinCompany(createClientDTO, companyId);
        return ResponseEntity.status(201).body(newClient);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClientDTO> updateClient(@PathVariable Long id, @RequestBody UpdateClientDTO updateClientDTO) {
        ClientDTO updatedClient = clientService.updateClient(id, updateClientDTO);
        return ResponseEntity.ok(updatedClient);
    }

    @GetMapping("/company/{companyId}")
    public ResponseEntity<List<ClientDTO>> getAllClientsByCompany(@PathVariable Long companyId) {
        List<ClientDTO> clients = clientService.getAllClientsByCompanyId(companyId);
        return ResponseEntity.ok(clients);
    }
}
