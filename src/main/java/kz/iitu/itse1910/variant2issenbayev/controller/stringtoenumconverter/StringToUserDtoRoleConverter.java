package kz.iitu.itse1910.variant2issenbayev.controller.stringtoenumconverter;

import kz.iitu.itse1910.variant2issenbayev.entity.User;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class StringToUserDtoRoleConverter implements Converter<String, User.Role> {
    @Override
    public User.Role convert(String source) {
        return Arrays.stream(User.Role.values())
                .filter(role -> role.toString().equals(source))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(source + " is not a valid User.Role enum value"));
    }
}
