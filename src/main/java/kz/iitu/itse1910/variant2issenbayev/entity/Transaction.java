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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Data
@Builder
@NoArgsConstructor
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, updatable = false)
    private Type type;

    @Column(name = "net_amount", nullable = false, updatable = false)
    private BigDecimal netAmount;

    @Column(nullable = false)
    private Status status;

    @Column(name = "created_at", columnDefinition = "TIMESTAMP DEFAULT NOW()",
            nullable = false, insertable = false, updatable = false)
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "created_by", nullable = false, updatable = false)
    private User createdBy;

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
        CANCELLED("CANCELLED");

        private final String value;

        Status(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return value;
        }
    }
}
