package com.boekhoud.backendboekhoudapplicatie.dal.implementatie;


import com.boekhoud.backendboekhoudapplicatie.dal.entity.Role;
import com.boekhoud.backendboekhoudapplicatie.dal.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RoleDal implements com.boekhoud.backendboekhoudapplicatie.service.dalinterface.RoleDal {

    private final RoleRepository roleRepository;

    @Autowired
    public RoleDal(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }
}
