package com.boekhoud.backendboekhoudapplicatie.dal.dalimpl;


import com.boekhoud.backendboekhoudapplicatie.service.dalinterface.RoleDal;
import com.boekhoud.backendboekhoudapplicatie.dal.entity.Role;
import com.boekhoud.backendboekhoudapplicatie.dal.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RoleDalImpl implements RoleDal {

    private final RoleRepository roleRepository;

    @Autowired
    public RoleDalImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }
}
