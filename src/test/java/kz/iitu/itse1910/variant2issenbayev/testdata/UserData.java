package kz.iitu.itse1910.variant2issenbayev.testdata;

import kz.iitu.itse1910.variant2issenbayev.entity.User;
import lombok.Getter;

@Getter
public class UserData {
    private final User admin = User.builder()
            .id(1L)
            .role(User.Role.ADMIN)
            .email("admin@stock.kz")
            .username("admin")
            .firstName("Bakhyt")
            .lastName("Bakhytzhanovich")
            .build();

    private final User salesman1 = User.builder()
            .id(2L)
            .role(User.Role.SALESMAN)
            .email("salesman1@stock.kz")
            .username("salesman1")
            .firstName("Anuar")
            .lastName("Altynbayev")
            .build();
}
