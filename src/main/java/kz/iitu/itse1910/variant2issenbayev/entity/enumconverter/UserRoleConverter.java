package kz.iitu.itse1910.variant2issenbayev.entity.enumconverter;

import kz.iitu.itse1910.variant2issenbayev.entity.User;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Arrays;

@Converter(autoApply = true)
public class UserRoleConverter implements AttributeConverter<User.Role, String> {
    @Override
    public String convertToDatabaseColumn(User.Role role) {
        return role.toString();
    }

    @Override
    public User.Role convertToEntityAttribute(String s) {
        return Arrays.stream(User.Role.values())
                .filter(role -> role.toString().equals(s))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(s + " is not a valid User.Role enum value"));
    }
}
