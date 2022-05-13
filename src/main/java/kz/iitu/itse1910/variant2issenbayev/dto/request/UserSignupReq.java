package kz.iitu.itse1910.variant2issenbayev.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserSignupReq {
    private final String email;
    private final String username;
    private final String password;
    private final String firstName;
    private final String lastName;
}
