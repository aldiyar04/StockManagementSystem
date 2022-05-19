package kz.iitu.itse1910.variant2issenbayev.dto.response;

import kz.iitu.itse1910.variant2issenbayev.entity.User;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class UserResp {
    private final Long id;
    private final User.Role role;
    private final String email;
    private final String username;
    private final String firstName;
    private final String lastName;
    private final LocalDate createdOn;
}
