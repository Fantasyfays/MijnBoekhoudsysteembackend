package com.boekhoud.backendboekhoudapplicatie.dal.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Client implements CompanyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String address;

    @NotNull
    @Size(min = 8, max = 20)
    private String kvkNumber;

    @NotNull
    @Size(min = 10, max = 20)
    private String taxNumber;

    private String bankAccountNumber;

    @NotNull
    @Email
    private String email;

    private String phoneNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "accountant_id")
    private Accountant accountant;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Invoice> invoices;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @Override
    public Company getCompany() {
        return this.company;
    }
}
