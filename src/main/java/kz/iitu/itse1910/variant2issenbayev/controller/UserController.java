package kz.iitu.itse1910.variant2issenbayev.controller;

import kz.iitu.itse1910.variant2issenbayev.dto.request.UserPasswdChangeReq;
import kz.iitu.itse1910.variant2issenbayev.dto.request.UserSignupReq;
import kz.iitu.itse1910.variant2issenbayev.dto.request.UserUpdateReq;
import kz.iitu.itse1910.variant2issenbayev.dto.response.UserResp;
import kz.iitu.itse1910.variant2issenbayev.entity.User;
import kz.iitu.itse1910.variant2issenbayev.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/users")
@PreAuthorize("denyAll()")
@AllArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    @PreAuthorize("permitAll()")
    public List<UserResp> getUsers(@RequestParam User.Role role) {
        return userService.getUsers(role);
    }

    @GetMapping("/{id}")
    @PreAuthorize("permitAll()")
    public UserResp getUserById(@PathVariable("id") long id) {
        return userService.getUserById(id);
    }

    @PostMapping
    @PreAuthorize("permitAll()")
    public UserResp registerUser(@Valid @RequestBody UserSignupReq signupReq) {
        return userService.registerUser(signupReq);
    }

    @PatchMapping("/{id}")
    @PreAuthorize("@userSecurity.hasUserId(#authentication, #id)")
    public void changePassword(@PathVariable("id") long id, @Valid @RequestBody UserPasswdChangeReq passwdChangeReq,
                               Authentication authentication) {
        userService.changePassword(id, passwdChangeReq);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or @userSecurity.hasUserId(#authentication, #id)")
    public UserResp updateUser(@PathVariable("id") long id, @Valid @RequestBody UserUpdateReq userUpdateReq,
                               Authentication authentication) {
        return userService.updateUser(id, userUpdateReq);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<?> deleteUser(@PathVariable("id") long id) {
        userService.deleteUser(id);
        // Not using @ResponseStatus since it doesn't let the right HTTP status
        // to be caught by RequestResponseLoggingAspect
        return ResponseEntity.noContent().build();
    }
}
