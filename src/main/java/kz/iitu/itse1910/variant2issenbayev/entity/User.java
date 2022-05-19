package kz.iitu.itse1910.variant2issenbayev.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.Hibernate;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.Table;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "users")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, updatable = false)
    private Role role;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(columnDefinition = "CHAR(60)", nullable = false)
    private String password;

    @Column(name = "fname", nullable = false)
    private String firstName;

    @Column(name = "lname", nullable = false)
    private String lastName;

    @Column(name = "created_on", nullable = false, updatable = false)
    private LocalDate createdOn;

    @OneToMany(mappedBy = "createdBy", fetch = FetchType.LAZY)
    private List<Transaction> transactions;

    @PrePersist
    private void setCreatedOn() {
        createdOn = LocalDate.now();
    }

    @PreRemove
    public void preRemove() {
        transactions.forEach(tx -> tx.setCreatedBy(null));
    }

    public boolean isAdmin() {
        return role == Role.ADMIN;
    }

    public boolean hasAssociatedTransactions() {
        Hibernate.initialize(this);
        return transactions.isEmpty();
    }

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
