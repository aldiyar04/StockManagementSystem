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
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "customers")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fname", nullable = false)
    private String firstName;

    @Column(name = "lname", nullable = false)
    private String lastName;

    @Column(nullable = false)
    private String phone;

    @Column(nullable = false)
    private String email;

    @Column(name = "card_number", columnDefinition = "CHAR(19)", unique = true, nullable = false)
    private String cardNumber;

    @Column(name = "bonus_balance", nullable = false)
    private BigDecimal bonusBalance;

    @Column(name = "created_on", nullable = false, updatable = false)
    private LocalDate createdOn;

    @PrePersist
    public void setCreatedOn() {
        createdOn = LocalDate.now();
    }

    @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY)
    private List<SaleTransaction> transactions;

    public List<SaleTransaction> fetchTransactions() {
        Hibernate.initialize(transactions);
        return transactions;
    }

    public boolean hasAssociatedTransactions() {
        Hibernate.initialize(transactions);
        return !transactions.isEmpty();
    }

    public String getFirstLastName() {
        return firstName + " " + lastName;
    }
}
