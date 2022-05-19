package kz.iitu.itse1910.variant2issenbayev;

import kz.iitu.itse1910.variant2issenbayev.entity.Supplier;
import kz.iitu.itse1910.variant2issenbayev.entity.User;
import kz.iitu.itse1910.variant2issenbayev.repository.SupplierRepository;
import kz.iitu.itse1910.variant2issenbayev.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class DatabaseInitializer implements CommandLineRunner {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final SupplierRepository supplierRepository;

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

        Supplier supplier1 = Supplier.builder()
                .name("Performance Food Group")
                .phone("+1 (804) 484-7700")
                .websiteUrl("www.pfgc.com")
                .city("Richmont")
                .street("West Creek Parkway")
                .buildingNumber("12500")
                .build();

        Supplier supplier2 = Supplier.builder()
                .name("Reyes Holdings")
                .phone("+1 (800) 536-6070")
                .email("supplies@reyesholding.com")
                .websiteUrl("www.reyesholdings.com")
                .city("Resemont")
                .street("North River Road")
                .buildingNumber("6250")
                .build();

        Supplier supplier3 = Supplier.builder()
                .name("Gordon Food Service")
                .phone("+1 (616) 530-7000")
                .email("service@gfs.com")
                .websiteUrl("www.gfs.com")
                .city("Wyoming")
                .street("Gezon Parkway SW")
                .buildingNumber("1300")
                .build();

        List.of(supplier1, supplier2, supplier3).forEach(supplierRepository::save);


//        SaleTransaction saleTx = SaleTransaction.builder()
//                .netAmount(new BigDecimal("4000"))
//                .status(Transaction.Status.COMPLETED)
//                .createdBy(salesman1)
//                .build();
    }
}
