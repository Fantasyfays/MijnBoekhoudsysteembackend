package com.boekhoud.backendboekhoudapplicatie.dal.implementatie;

import com.boekhoud.backendboekhoudapplicatie.dal.entity.Client;
import com.boekhoud.backendboekhoudapplicatie.dal.repository.ClientRepository;
import com.boekhoud.backendboekhoudapplicatie.service.dalinterface.IClientDal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientDal implements IClientDal {

    private final ClientRepository clientRepository;

    @Autowired
    public ClientDal(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public List<Client> findAll() {
        return clientRepository.findAll();
    }

    @Override
    public Optional<Client> findById(Long id) {
        return clientRepository.findById(id);
    }

    @Override
    public List<Client> findByAccountantId(Long accountantId) {
        return clientRepository.findByAccountantId(accountantId);
    }

    @Override
    public List<Client> findByCompanyId(Long companyId) {
        return clientRepository.findByCompanyId(companyId);
    }

    @Override
    public Client save(Client client) {
        return clientRepository.save(client);
    }

    @Override
    public void deleteById(Long id) {
        clientRepository.deleteById(id);
    }
}
