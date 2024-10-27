package com.boekhoud.backendboekhoudapplicatie.model;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String address;
    private String city;
    private String zip;
    private String country;
    private String phone;
    private String email;
    private String btwNumber;
    private String kvkNumber;
    private String bankNumber;

    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL)
    private List<Accountant> accountants;

    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL)
    private List<Client> clients;

}
