package kz.iitu.itse1910.variant2issenbayev;

import kz.iitu.itse1910.variant2issenbayev.entity.Category;
import kz.iitu.itse1910.variant2issenbayev.entity.Product;
import kz.iitu.itse1910.variant2issenbayev.entity.Supplier;
import kz.iitu.itse1910.variant2issenbayev.entity.Uom;
import kz.iitu.itse1910.variant2issenbayev.entity.User;
import kz.iitu.itse1910.variant2issenbayev.repository.CategoryRepository;
import kz.iitu.itse1910.variant2issenbayev.repository.SupplierRepository;
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

//        Category category1 = new Category("Bakery and Bread");
//        Category category2 = new Category("Meat and Seafood");
//        Category category3 = new Category("Pasta and Rice");
//        Category category4 = new Category("Oils, Sauces, Salad Dressings, and Condiments");
//        Category category5 = new Category("Cereals and Breakfast Foods");
//        Category category6 = new Category("Soups and Canned Goods");
//        Category category7 = new Category("Frozen Foods");
//        Category category8 = new Category("Dairy, Cheese, and Eggs");
//        Category category9 = new Category("Snacks and Crackers");
//        Category category10 = new Category("Produce");
//        Category category11 = new Category("Drinks");
//        List<Category> categories = List.of(
//                category1, category2, category3, category4, category5,
//                category6, category7, category8, category9, category10, category11
//        );
//        categories.forEach(categoryRepository::save);

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

//        SaleTransaction saleTx = SaleTransaction.builder()
//                .netAmount(new BigDecimal("4000"))
//                .status(Transaction.Status.COMPLETED)
//                .createdBy(salesman1)
//                .build();
    }
}
