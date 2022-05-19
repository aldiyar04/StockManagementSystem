package kz.iitu.itse1910.variant2issenbayev.security;

import kz.iitu.itse1910.variant2issenbayev.dto.request.UserLoginReq;
import kz.iitu.itse1910.variant2issenbayev.dto.response.JwtUserResp;
import kz.iitu.itse1910.variant2issenbayev.entity.User;
import kz.iitu.itse1910.variant2issenbayev.exception.InvalidCredentialsException;
import kz.iitu.itse1910.variant2issenbayev.mapper.UserMapper;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class UserSecurity {
    private final AuthenticationManager authManager;
    private final JwtUtil jwtUtil;

    public JwtUserResp authenticateUser(UserLoginReq loginReq) {
        Authentication auth;
        try {
            auth = authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginReq.getUsername(), loginReq.getPassword())
            );
        } catch (BadCredentialsException e) {
            String msg = e.getMessage();
            throw new InvalidCredentialsException();
        }

        User user = getUser(auth);
        return new JwtUserResp(
                jwtUtil.generateToken(user),
                UserMapper.INSTANCE.toDto(user)
        );
    }

    public boolean hasUserId(Authentication auth, long id) {
        User user = getUser(auth);
        return user.getId() == id;
    }

    private User getUser(Authentication auth) {
        UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();
        return userDetails.getUser();
    }
}
