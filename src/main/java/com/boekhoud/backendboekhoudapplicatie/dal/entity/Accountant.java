package com.boekhoud.backendboekhoudapplicatie.dal.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Accountant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Link to User for authentication and authorization
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    // Link to Company the accountant works for
    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

    // Accountant-specific fields
    private String firstName;
    private String lastName;
    private String employeeId; // Unique ID for the accountant in the company
    private String email;
    private String phoneNumber;
    private String address;

}
