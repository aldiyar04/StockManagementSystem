package kz.iitu.itse1910.variant2issenbayev.entity;

import kz.iitu.itse1910.variant2issenbayev.security.UserDetailsImpl;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.Hibernate;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "transactions")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Data
@SuperBuilder
@NoArgsConstructor
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @Column(nullable = false, updatable = false)
//    private Type type;

    @Column(name = "net_amount", nullable = false, updatable = false)
    private BigDecimal netAmount;

    @Column(nullable = false)
    private Status status;

    @Column(name = "refund_amount")
    private BigDecimal refundAmount;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "created_by", nullable = false, updatable = false)
    private User createdBy;

    @OneToMany(mappedBy = "transaction", fetch = FetchType.LAZY, orphanRemoval = true)
    @Cascade({CascadeType.SAVE_UPDATE, CascadeType.DELETE})
    private List<TransactionItem> items;

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
        if (getPrincipalUser() != null) {
            createdBy = getPrincipalUser();
        }
        if (status != null) {
            status = Status.COMPLETED;
        }
        netAmount = items.stream().map(TransactionItem::getNetAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public List<TransactionItem> fetchItems() {
        Hibernate.initialize(items);
        return items;
    }

    public enum Type {
        PURCHASE("PURCHASE"), SALE("SALE");

        private final String value;

        Type(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return value;
        }
    }

    public enum Status {
        PENDING("PENDING"), IN_PROGRESS("IN_PROGRESS"), COMPLETED("COMPLETED"),
        REFUNDED("REFUNDED");

        private final String value;

        Status(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return value;
        }
    }

    private User getPrincipalUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) {
            return null;
        }
        UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();
        return userDetails.getUser();
    }
}
