package kz.iitu.itse1910.variant2issenbayev.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.Hibernate;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.AssertTrue;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "products")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    @Column(length = 2000)
    private String description;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @ManyToOne
    @JoinColumn(name = "supplier_id", nullable = false)
    private Supplier supplier;

    @Column(name = "wholesale_price", nullable = false)
    private BigDecimal wholesalePrice;

    @Column(name = "retail_price", nullable = false)
    private BigDecimal retailPrice;

    @Column(nullable = false)
    private BigDecimal quantity;

    @OneToOne
    @JoinColumn(name = "uom_id", unique = true)
    @Cascade(CascadeType.SAVE_UPDATE)
    private Uom uom;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    private List<TransactionItem> transactionItems;

    public boolean hasAssociatedTransactions() {
        Hibernate.initialize(transactionItems);
        return !transactionItems.isEmpty();
    }

    @AssertTrue(message = "Retail price cannot be <= wholesale price")
    public boolean isProfitPositive() {
        return retailPrice.compareTo(wholesalePrice) > 0;
    }
}
