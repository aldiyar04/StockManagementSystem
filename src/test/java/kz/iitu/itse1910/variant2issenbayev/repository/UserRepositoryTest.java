package kz.iitu.itse1910.variant2issenbayev.repository;

import kz.iitu.itse1910.variant2issenbayev.DatabaseInitializer;
import kz.iitu.itse1910.variant2issenbayev.Variant2issenbayevApplication;
import kz.iitu.itse1910.variant2issenbayev.config.HibernateConfig;
import kz.iitu.itse1910.variant2issenbayev.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {Variant2issenbayevApplication.class, HibernateConfig.class, UserRepository.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class UserRepositoryTest {
    UserRepository underTest;
    DatabaseInitializer dbInitializer;

    @Autowired
    UserRepositoryTest(UserRepository underTest, DatabaseInitializer dbInitializer) {
        this.underTest = underTest;
        this.dbInitializer = dbInitializer;
    }

    @BeforeEach
    void populateDatabase() throws Exception {
        dbInitializer.run();
    }

    @Test
    void testFindAllByRole_admin() {
        List<User> result = underTest.findAllByRole(User.Role.ADMIN);
        assertThat(result.size()).isEqualTo(1);
    }

    @Test
    void testFindAllByRole_salesman() {
        List<User> result = underTest.findAllByRole(User.Role.SALESMAN);
        assertThat(result.size()).isEqualTo(2);
    }

    @Test
    void testFindById_caseFound() {
        Optional<User> result = underTest.findById(1);
        assertThat(result.isPresent()).isTrue();
    }

    @Test
    void testFindById_caseNotFound() {
        Optional<User> result = underTest.findById(0);
        assertThat(result.isPresent()).isFalse();
    }

    @Test
    void testFindByEmail_caseFound() {
        Optional<User> result = underTest.findByEmail("admin@stock.kz");
        assertThat(result.isPresent()).isTrue();
    }

    @Test
    void testFindByEmail_caseNotFound() {
        Optional<User> result = underTest.findByEmail("nonexisting@email.com");
        assertThat(result.isPresent()).isFalse();
    }

    @Test
    void testFindByUsername_caseFound() {
        Optional<User> result = underTest.findByUsername("admin");
        assertThat(result.isPresent()).isTrue();
    }

    @Test
    void testFindByUsername_caseNotFound() {
        Optional<User> result = underTest.findByUsername("nonexisting");
        assertThat(result.isPresent()).isFalse();
    }

    @Test
    void testSave() {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        User newSalesman = User.builder()
                .role(User.Role.SALESMAN)
                .email("newsalesman@stock.kz")
                .username("newsalesman")
                .password(passwordEncoder.encode("pass"))
                .firstName("Maria")
                .lastName("Stepanchuk")
                .build();

        underTest.save(newSalesman);

        List<User> salesmen = underTest.findAllByRole(User.Role.SALESMAN);
        Optional<User> result = salesmen.stream()
                .filter(salesman -> salesman.getUsername().equals("newsalesman"))
                .findFirst();
        assertThat(result.isPresent()).isTrue();
    }

    @Test
    void testDelete() {
        Optional<User> salesman2 = underTest.findById(3);
        assertThat(salesman2.isPresent()).isTrue();
        underTest.delete(salesman2.get());

        assertThat(underTest.findById(3).isPresent()).isFalse();
    }
}