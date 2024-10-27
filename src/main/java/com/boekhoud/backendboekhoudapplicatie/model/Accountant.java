package com.boekhoud.backendboekhoudapplicatie.model;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Accountant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;
    private String email;
    private Long phoneNumber;
    private LocalDate dateOfBirth;
    private String function;

@ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

@OneToMany(mappedBy = "accountant", cascade = CascadeType.ALL)
    private List<Client> clients;




}
