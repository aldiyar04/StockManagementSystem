package kz.iitu.itse1910.variant2issenbayev.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "transaction_products")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "transaction_id", nullable = false, updatable = false)
    private Transaction transaction;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false, updatable = false)
    private Product product;

    @Column(nullable = false, updatable = false)
    private BigDecimal quantity;

    @Column(name = "net_amount", nullable = false, updatable = false)
    private BigDecimal netAmount;

    @OneToOne
    @Cascade(CascadeType.SAVE_UPDATE)
    @JoinColumn(name = "uom_id", unique = true, updatable = false)
    private Uom uom;

    @Builder
    public TransactionItem(Transaction transaction, Product product, BigDecimal quantity) {
        this.transaction = transaction;
        this.product = product;
        this.quantity = quantity;
        netAmount = product.getRetailPrice().multiply(quantity);
        uom = product.getUom();
    }
}
