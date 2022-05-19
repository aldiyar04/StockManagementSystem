package kz.iitu.itse1910.variant2issenbayev.entity;

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

@Entity
@Table(name = "uoms")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Data
@NoArgsConstructor
public class Uom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "purchase_uom", nullable = false, updatable = false)
    private String purchaseUom;

    @Column(name = "sale_uom", nullable = false, updatable = false)
    private String saleUom;

    @Column(name = "conversion_rate", nullable = false, updatable = false)
    private Integer conversionRate;

    public Uom(String purchaseUom, String saleUom, Integer conversionRate) {
        this.purchaseUom = purchaseUom;
        this.saleUom = saleUom;
        this.conversionRate = conversionRate;
    }
}
