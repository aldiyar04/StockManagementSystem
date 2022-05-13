package kz.iitu.itse1910.variant2issenbayev.entity.enumconverter;

import kz.iitu.itse1910.variant2issenbayev.entity.Transaction;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Arrays;

@Converter(autoApply = true)
public class TransactionTypeConverter implements AttributeConverter<Transaction.Type, String> {
    @Override
    public String convertToDatabaseColumn(Transaction.Type type) {
        return type.toString();
    }

    @Override
    public Transaction.Type convertToEntityAttribute(String s) {
        return Arrays.stream(Transaction.Type.values())
                .filter(type -> type.toString().equals(s))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(s + " is not a valid Transaction.Type enum value"));
    }
}
