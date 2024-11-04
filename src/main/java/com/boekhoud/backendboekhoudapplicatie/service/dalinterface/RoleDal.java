package com.boekhoud.backendboekhoudapplicatie.service.dalinterface;

import com.boekhoud.backendboekhoudapplicatie.dal.entity.Role;
import java.util.List;

public interface RoleDal {
    List<Role> getAllRoles();
}
