package com.boekhoud.backendboekhoudapplicatie.service;

import com.boekhoud.backendboekhoudapplicatie.service.dalinterface.RoleDal;
import com.boekhoud.backendboekhoudapplicatie.dal.entity.Role;
import com.boekhoud.backendboekhoudapplicatie.dto.RoleDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleService {

    private final RoleDal roleDal;

    @Autowired
    public RoleService(RoleDal roleDal) {
        this.roleDal = roleDal;
    }

    public List<RoleDTO> getAllRoles() {
        List<Role> roles = roleDal.getAllRoles();
        return roles.stream()
                .map(role -> new RoleDTO(role.getId(), role.getName()))
                .collect(Collectors.toList());
    }
}
