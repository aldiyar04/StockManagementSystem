package kz.iitu.itse1910.variant2issenbayev;

import kz.iitu.itse1910.variant2issenbayev.entity.User;
import kz.iitu.itse1910.variant2issenbayev.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class DatabaseInitializer implements CommandLineRunner {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // Save users
        User admin = User.builder()
                .role(User.Role.ADMIN)
                .email("admin@stock.kz")
                .username("admin")
                .password(passwordEncoder.encode("pass"))
                .firstName("Bakhyt")
                .lastName("Bakhytzhanovich")
                .build();
        User salesman1 = User.builder()
                .role(User.Role.SALESMAN)
                .email("salesman1@stock.kz")
                .username("salesman1")
                .password(passwordEncoder.encode("pass"))
                .firstName("Anuar")
                .lastName("Altynbayev")
                .build();
        User salesman2 = User.builder()
                .role(User.Role.SALESMAN)
                .email("salesman2@stock.kz")
                .username("salesman2")
                .password(passwordEncoder.encode("pass"))
                .firstName("Alexander")
                .lastName("Pack")
                .build();
        List.of(admin, salesman1, salesman2).forEach(userRepository::save);

//        SaleTransaction saleTx = SaleTransaction.builder()
//                .netAmount(new BigDecimal("4000"))
//                .status(Transaction.Status.COMPLETED)
//                .createdBy(salesman1)
//                .build();
    }
}
