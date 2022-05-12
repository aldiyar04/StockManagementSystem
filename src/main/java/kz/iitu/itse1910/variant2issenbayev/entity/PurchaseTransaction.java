package kz.iitu.itse1910.variant2issenbayev.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@DiscriminatorValue("PURCHASE")
@SuperBuilder
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class PurchaseTransaction extends Transaction {
    @ManyToOne
    @JoinColumn(name = "supplier_id", updatable = false)
    private Supplier supplier;
}
