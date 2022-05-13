package kz.iitu.itse1910.variant2issenbayev.dto.request;

import lombok.Data;

@Data
public class UserLoginReq {
    private final String username;
    private final String password;
}
