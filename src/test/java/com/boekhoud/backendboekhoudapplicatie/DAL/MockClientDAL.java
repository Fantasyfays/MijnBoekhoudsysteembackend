package com.boekhoud.backendboekhoudapplicatie.DAL;

import com.boekhoud.backendboekhoudapplicatie.dal.entity.Client;
import com.boekhoud.backendboekhoudapplicatie.service.dalinterface.IClientDal;

import java.util.*;

public class MockClientDAL implements IClientDal {

    private Map<Long, Client> clientDatabase = new HashMap<>();
    private Long currentId = 1L;

    @Override
    public List<Client> findAll() {
        return new ArrayList<>(clientDatabase.values());
    }

    @Override
    public Optional<Client> findById(Long id) {
        return Optional.ofNullable(clientDatabase.get(id));
    }

    @Override
    public List<Client> findByAccountantId(Long accountantId) {
        // Voor deze mock, retourneer alle clients als ze een accountantId hebben (je kunt dit uitbreiden zoals nodig)
        return new ArrayList<>(clientDatabase.values());
    }

    @Override
    public List<Client> findByCompanyId(Long companyId) {
        // Zelfde hier, voor het gemak mocken we dit
        return new ArrayList<>(clientDatabase.values());
    }

    @Override
    public Client save(Client client) {
        if (client.getId() == null) {
            client.setId(currentId++);
        }
        clientDatabase.put(client.getId(), client);
        return client;
    }

    @Override
    public void deleteById(Long id) {
        clientDatabase.remove(id);
    }

    // Methode om een specifieke client op te halen voor testen
    public Client getSavedClient() {
        if (clientDatabase.isEmpty()) {
            return null;
        }
        // Retourneer de laatst opgeslagen client (meest recent toegevoegde)
        return clientDatabase.get(currentId - 1);
    }
}
