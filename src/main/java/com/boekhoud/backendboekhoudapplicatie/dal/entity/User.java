package com.boekhoud.backendboekhoudapplicatie.dal.entity;

import com.boekhoud.backendboekhoudapplicatie.enums.RoleType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String username;

    @JsonIgnore
    private String password;

    @Enumerated(EnumType.STRING)
    private RoleType role;
    private String email;

}