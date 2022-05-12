package kz.iitu.itse1910.variant2issenbayev.entity;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "users")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Data
@Builder
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "VARCHAR(255) CHECK (role IN ('ADMIN', 'SALESMAN'))", nullable = false)
    private Role role;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(columnDefinition = "CHAR(28)", nullable = false)
    private String password;

    @Column(name = "fname", nullable = false)
    private String firstName;

    @Column(name = "lname", nullable = false)
    private String lastName;

    @Column(name = "created_on", columnDefinition = "DATE DEFAULT CURRENT_DATE",
            nullable = false, insertable = false, updatable = false)
    private LocalDate createdOn;

    public enum Role {
        ADMIN("ADMIN"), SALESMAN("SALESMAN");

        private final String value;

        Role(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return value;
        }
    }
}
