package com.boekhoud.backendboekhoudapplicatie.model;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String address;
    private String city;
    private String zip;
    private String country;
    private String companyName;
    private String btwNumber;
    private String kvkNumber;
    private String bankNumber;
    private String billingAddress;
    private String billingCity;
    private String billingCountry;
    private String billingZip;
    private String clientCategory;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

    @ManyToOne
    @JoinColumn(name = "accountant_id")
    private Accountant accountant;
}
