package com.boekhoud.backendboekhoudapplicatie.DAL;

import com.boekhoud.backendboekhoudapplicatie.dal.entity.Client;
import com.boekhoud.backendboekhoudapplicatie.service.dalinterface.IClientDal;

import java.util.*;

public class MockClientDAL implements IClientDal {

    private Map<Long, Client> clientDatabase = new HashMap<>();
    private Map<String, Client> usernameToClientMap = new HashMap<>(); // Voor username-lookup
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
    public Optional<Client> findByUsername(String username) {
        return Optional.ofNullable(usernameToClientMap.get(username));
    }

    @Override
    public Client save(Client client) {
        if (client.getId() == null) {
            client.setId(currentId++);
        }
        clientDatabase.put(client.getId(), client);

        // Zorg ervoor dat de username wordt gekoppeld aan de client
        if (client.getUser() != null && client.getUser().getUsername() != null) {
            usernameToClientMap.put(client.getUser().getUsername(), client);
        }
        return client;
    }

    @Override
    public void deleteById(Long id) {
        Client client = clientDatabase.remove(id);
        if (client != null && client.getUser() != null) {
            usernameToClientMap.remove(client.getUser().getUsername());
        }
    }

    public Client getSavedClient() {
        if (clientDatabase.isEmpty()) {
            return null;
        }
        return clientDatabase.get(currentId - 1);
    }
}
