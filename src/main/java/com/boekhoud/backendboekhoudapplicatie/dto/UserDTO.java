// UserDTO.java
package com.boekhoud.backendboekhoudapplicatie.dto;

import com.boekhoud.backendboekhoudapplicatie.dal.entity.RoleType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private Long id;
    private String username;
    private String password;
    private RoleType role;
}
