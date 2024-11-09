package com.boekhoud.backendboekhoudapplicatie.service.dalinterface;

import com.boekhoud.backendboekhoudapplicatie.dal.entity.User;
import java.util.List;
import java.util.Optional;

public interface IUserDal {
    List<User> findAll();
    Optional<User> findById(Long id);
    User save(User user);
    void deleteById(Long id);
    Optional<User> findByUsername(String username);
}
