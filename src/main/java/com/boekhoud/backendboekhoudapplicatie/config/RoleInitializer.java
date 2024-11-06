package com.boekhoud.backendboekhoudapplicatie.config;

import com.boekhoud.backendboekhoudapplicatie.dal.entity.Role;
import com.boekhoud.backendboekhoudapplicatie.dal.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class RoleInitializer {

    @Bean
    public CommandLineRunner initializeRoles(RoleRepository roleRepository) {
        return args -> {
            Map<String, String> roles = Map.of(
                    "ADMIN", "Beheerder van de applicatie met volledige toegangsrechten",
                    "ACCOUNTANTS BEDRIJF BEHEERDER", "Beheerder van het accountantsbedrijf, met rechten om klantbedrijven en accountants te beheren",
                    "ACCOUNTANT", "Accountant in het accountantsbedrijf, verantwoordelijk voor het beheren van klantgegevens",
                    "GEBRUIKER KLANTBEDRIJF", "Gebruiker binnen een klantbedrijf met toegang tot de eigen bedrijfsgegevens en financiën"
            );

            roles.forEach((roleName, description) -> {
                if (roleRepository.findByName(roleName).isEmpty()) {
                    Role role = new Role();
                    role.setName(roleName);
                    role.setDescription(description);
                    roleRepository.save(role);
                }
            });
        };
    }
}
