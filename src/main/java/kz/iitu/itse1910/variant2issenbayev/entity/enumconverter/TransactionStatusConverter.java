package kz.iitu.itse1910.variant2issenbayev.entity.enumconverter;

import kz.iitu.itse1910.variant2issenbayev.entity.Transaction;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Arrays;

@Converter(autoApply = true)
public class TransactionStatusConverter implements AttributeConverter<Transaction.Status, String> {
    @Override
    public String convertToDatabaseColumn(Transaction.Status status) {
        return status.toString();
    }

    @Override
    public Transaction.Status convertToEntityAttribute(String s) {
        return Arrays.stream(Transaction.Status.values())
                .filter(type -> type.toString().equals(s))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(s + " is not a valid Transaction.Status enum value"));
    }
}
