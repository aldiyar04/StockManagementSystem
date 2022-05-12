package kz.iitu.itse1910.variant2issenbayev.entity;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "customers")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Data
@Builder
@NoArgsConstructor
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fname", nullable = false)
    private String firstName;

    @Column(name = "lname", nullable = false)
    private String lastName;

    @Column(unique = true, nullable = false)
    private String phone;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(name = "card_number", columnDefinition = "CHAR(16)", unique = true, nullable = false)
    private String cardNumber;

    @Column(name = "bonus_balance", nullable = false)
    private BigDecimal bonusBalance;

    @Column(name = "created_on", columnDefinition = "DATE DEFAULT CURRENT_DATE",
            nullable = false, insertable = false, updatable = false)
    private LocalDate createdOn;
}
