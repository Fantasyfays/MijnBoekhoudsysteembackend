package com.boekhoud.backendboekhoudapplicatie.dal;

import com.boekhoud.backendboekhoudapplicatie.dal.entity.User;
import com.boekhoud.backendboekhoudapplicatie.dal.implementatie.UserDal;
import com.boekhoud.backendboekhoudapplicatie.service.dalinterface.IUserDal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class MockUserDal implements IUserDal {

    private final Map<Long, User> userStorage = new HashMap<>();
    private long currentId = 1L;

    @Override
    public List<User> findAll() {
        return new ArrayList<>(userStorage.values());
    }

    @Override
    public Optional<User> findById(Long id) {
        return Optional.ofNullable(userStorage.get(id));
    }

    @Override
    public User save(User user) {
        if (user.getId() == null) {
            user.setId(currentId++);
        }
        userStorage.put(user.getId(), user);
        return user;
    }

    @Override
    public void deleteById(Long id) {
        userStorage.remove(id);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userStorage.values().stream()
                .filter(user -> username.equals(user.getUsername()))
                .findFirst();
    }

    public void clearStorage() {
        userStorage.clear();
        currentId = 1L;
    }
}
