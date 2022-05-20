package kz.iitu.itse1910.variant2issenbayev;

import kz.iitu.itse1910.variant2issenbayev.entity.Category;
import kz.iitu.itse1910.variant2issenbayev.entity.Customer;
import kz.iitu.itse1910.variant2issenbayev.entity.Product;
import kz.iitu.itse1910.variant2issenbayev.entity.SaleTransaction;
import kz.iitu.itse1910.variant2issenbayev.entity.Supplier;
import kz.iitu.itse1910.variant2issenbayev.entity.Transaction;
import kz.iitu.itse1910.variant2issenbayev.entity.TransactionItem;
import kz.iitu.itse1910.variant2issenbayev.entity.Uom;
import kz.iitu.itse1910.variant2issenbayev.entity.User;
import kz.iitu.itse1910.variant2issenbayev.repository.CategoryRepository;
import kz.iitu.itse1910.variant2issenbayev.repository.CustomerRepository;
import kz.iitu.itse1910.variant2issenbayev.repository.SupplierRepository;
import kz.iitu.itse1910.variant2issenbayev.repository.TransactionRepository;
import kz.iitu.itse1910.variant2issenbayev.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@Component
@AllArgsConstructor
public class DatabaseInitializer implements CommandLineRunner {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final SupplierRepository supplierRepository;
    private final CategoryRepository categoryRepository;
    private final CustomerRepository customerRepository;
    private final TransactionRepository txRepo;

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
//        List.of(admin, salesman1, salesman2).forEach(userRepository::save);
        userRepository.saveAll(List.of(admin, salesman1, salesman2));

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


        Category categoryCanned = new Category("Canned");
        Product productSoup = Product.builder()
                .name("Soup")
                .category(categoryCanned)
                .supplier(supplier1)
                .wholesalePrice(new BigDecimal("2"))
                .retailPrice(new BigDecimal("2.5"))
                .quantity(new BigDecimal("50"))
                .uom(new Uom("box", "packet", 10))
                .build();
        Product productTuna = Product.builder()
                .name("Tuna")
                .category(categoryCanned)
                .supplier(supplier1)
                .wholesalePrice(new BigDecimal("1.3"))
                .retailPrice(new BigDecimal("1.7"))
                .quantity(new BigDecimal("32"))
                .uom(new Uom("box", "packet", 12))
                .build();
        categoryCanned.setProducts(Arrays.asList(productSoup, productTuna));

        Category categoryCondimentsSpices = new Category("Condiments & Spices");
        Product productBlackPepper = Product.builder()
                .name("Black Pepper")
                .category(categoryCondimentsSpices)
                .supplier(supplier2)
                .wholesalePrice(new BigDecimal("0.7"))
                .retailPrice(new BigDecimal("0.8"))
                .quantity(new BigDecimal("138"))
                .uom(new Uom("kg", "each", 20))
                .build();
        Product productOliveOil = Product.builder()
                .name("Olive Oil")
                .category(categoryCondimentsSpices)
                .supplier(supplier2)
                .wholesalePrice(new BigDecimal("2.3"))
                .retailPrice(new BigDecimal("2.5"))
                .quantity(new BigDecimal("25"))
                .uom(new Uom("l", "bottle", 2))
                .build();
        categoryCondimentsSpices.setProducts(Arrays.asList(productBlackPepper, productOliveOil));

        Category categoryFruits = new Category("Fruits");
        Product productApples = Product.builder()
                .name("Apples")
                .category(categoryFruits)
                .supplier(supplier3)
                .wholesalePrice(new BigDecimal("2.835457"))
                .retailPrice(new BigDecimal("3"))
                .quantity(new BigDecimal("40.5"))
                .uom(new Uom("kg", "kg", 1))
                .build();
        Product productBananas = Product.builder()
                .name("Bananas")
                .category(categoryFruits)
                .supplier(supplier3)
                .wholesalePrice(new BigDecimal("2.74802"))
                .retailPrice(new BigDecimal("2.9"))
                .quantity(new BigDecimal("37.2"))
                .uom(new Uom("kg", "kg", 1))
                .build();
        categoryFruits.setProducts(Arrays.asList(productApples, productBananas));

        Category categoryVegetables = new Category("Vegetables");
        Product productPotatoes = Product.builder()
                .name("Potatoes")
                .category(categoryVegetables)
                .supplier(supplier3)
                .wholesalePrice(new BigDecimal("1.3247"))
                .retailPrice(new BigDecimal("2"))
                .quantity(new BigDecimal("20.6"))
                .uom(new Uom("kg", "kg", 1))
                .build();
        Product productOnions = Product.builder()
                .name("Onions")
                .category(categoryVegetables)
                .supplier(supplier3)
                .wholesalePrice(new BigDecimal("1.35"))
                .retailPrice(new BigDecimal("1.9"))
                .quantity(new BigDecimal("25.8"))
                .uom(new Uom("kg", "kg", 1))
                .build();
        categoryVegetables.setProducts(Arrays.asList(productPotatoes, productOnions));

        List<Category> categories = List.of(categoryCanned, categoryCondimentsSpices,
                categoryFruits, categoryVegetables);
        categories.forEach(categoryRepository::save);


        Customer customer1 = Customer.builder()
                .firstName("Dominic")
                .lastName("Roberts")
                .phone("+1 (812) 853-2813")
                .email("proberts@gmail.com")
                .cardNumber("3594-3590-9064-1590")
                .bonusBalance(new BigDecimal("0"))
                .build();
        Customer customer2 = Customer.builder()
                .firstName("Theo")
                .lastName("Ryan")
                .phone("+1 (812) 853-2813")
                .email("tryan@gmail.com")
                .cardNumber("0460-6590-3489-6904")
                .bonusBalance(new BigDecimal("0"))
                .build();
        Customer customer3 = Customer.builder()
                .firstName("Stephen")
                .lastName("Morales")
                .phone("+1 (812) 853-2813")
                .email("smorales@gmail.com")
                .cardNumber("3599-8693-8954-5491")
                .bonusBalance(new BigDecimal("0"))
                .build();
        List.of(customer1, customer2, customer3).forEach(customerRepository::save);




        SaleTransaction tx1 = SaleTransaction.builder()
                .customer(customer1)
                .status(Transaction.Status.COMPLETED)
                .createdBy(salesman1)
                .build();
        TransactionItem tx1it1 = TransactionItem.builder()
                .transaction(tx1)
                .product(productSoup)
                .quantity(new BigDecimal("1"))
                .build();
        TransactionItem tx1it2 = TransactionItem.builder()
                .transaction(tx1)
                .product(productApples)
                .quantity(new BigDecimal("2"))
                .build();
        tx1.setItems(Arrays.asList(tx1it1, tx1it2));

        SaleTransaction tx2 = SaleTransaction.builder()
                .customer(customer2)
                .status(Transaction.Status.COMPLETED)
                .createdBy(salesman1)
                .build();
        TransactionItem tx2it1 = TransactionItem.builder()
                .transaction(tx2)
                .product(productOliveOil)
                .quantity(new BigDecimal("1"))
                .build();
        TransactionItem tx2it2 = TransactionItem.builder()
                .transaction(tx2)
                .product(productTuna)
                .quantity(new BigDecimal("2"))
                .build();
        tx2.setItems(Arrays.asList(tx2it1, tx2it2));

        SaleTransaction tx3 = SaleTransaction.builder()
                .customer(customer3)
                .status(Transaction.Status.COMPLETED)
                .createdBy(salesman2)
                .build();
        TransactionItem tx3it1 = TransactionItem.builder()
                .transaction(tx3)
                .product(productPotatoes)
                .quantity(new BigDecimal("3"))
                .build();
        TransactionItem tx3it2 = TransactionItem.builder()
                .transaction(tx3)
                .product(productOnions)
                .quantity(new BigDecimal("2.5"))
                .build();
        tx3.setItems(Arrays.asList(tx3it1, tx3it2));

        List.of(tx1, tx2, tx3).forEach(txRepo::save);

//        SaleTransaction saleTx = SaleTransaction.builder()
//                .netAmount(new BigDecimal("4000"))
//                .status(Transaction.Status.COMPLETED)
//                .createdBy(salesman1)
//                .build();
    }
}
