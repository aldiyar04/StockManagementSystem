package kz.iitu.itse1910.variant2issenbayev.dto.response;

import lombok.Data;

@Data
public class JwtUserResp {
    private final String jwtToken;
    private final UserResp user;
}
