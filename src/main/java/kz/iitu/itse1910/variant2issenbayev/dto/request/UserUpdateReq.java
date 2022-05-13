package kz.iitu.itse1910.variant2issenbayev.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserUpdateReq {
    private final String email;
    private final String username;
    private final String firstName;
    private final String lastName;
}
