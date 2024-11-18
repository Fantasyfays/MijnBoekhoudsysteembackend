package com.boekhoud.backendboekhoudapplicatie.service.dalinterface;

import com.boekhoud.backendboekhoudapplicatie.dal.entity.Client;
import java.util.List;
import java.util.Optional;

public interface IClientDal {

    List<Client> findAll();

    Optional<Client> findById(Long id);

    Client save(Client client);

    void deleteById(Long id);

    Optional<Client> findByUsername(String username);

}
