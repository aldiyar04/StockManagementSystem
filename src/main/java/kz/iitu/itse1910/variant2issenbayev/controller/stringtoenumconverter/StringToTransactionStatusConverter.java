package kz.iitu.itse1910.variant2issenbayev.controller.stringtoenumconverter;

import kz.iitu.itse1910.variant2issenbayev.entity.Transaction;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class StringToTransactionStatusConverter implements Converter<String, Transaction.Status> {
    @Override
    public Transaction.Status convert(String source) {
        return Arrays.stream(Transaction.Status.values())
                .filter(txStatus -> txStatus.toString().equals(source))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(source + " is not a valid Transaction.Status enum value"));
    }
}
