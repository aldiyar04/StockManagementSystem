package kz.iitu.itse1910.variant2issenbayev.controller;

import kz.iitu.itse1910.variant2issenbayev.dto.request.UserLoginReq;
import kz.iitu.itse1910.variant2issenbayev.dto.response.JwtUserResp;
import kz.iitu.itse1910.variant2issenbayev.security.UserSecurity;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.PermitAll;
import javax.validation.Valid;

@RestController
@AllArgsConstructor
public class AuthController {
    private final UserSecurity userSecurity;

    @PostMapping("/login")
    @PermitAll
    public ResponseEntity<JwtUserResp> authenticate(@Valid @RequestBody UserLoginReq loginReq) {
        JwtUserResp jwtUserResp = userSecurity.authenticateUser(loginReq);
        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, jwtUserResp.getJwtToken())
                .body(jwtUserResp);
    }
}
