package kz.iitu.itse1910.variant2issenbayev.service;

import kz.iitu.itse1910.variant2issenbayev.dto.request.UserPasswdChangeReq;
import kz.iitu.itse1910.variant2issenbayev.dto.request.UserSignupReq;
import kz.iitu.itse1910.variant2issenbayev.dto.request.UserUpdateReq;
import kz.iitu.itse1910.variant2issenbayev.dto.response.UserResp;
import kz.iitu.itse1910.variant2issenbayev.entity.User;
import kz.iitu.itse1910.variant2issenbayev.exception.InvalidCredentialsException;
import kz.iitu.itse1910.variant2issenbayev.exception.RecordAlreadyExistsException;
import kz.iitu.itse1910.variant2issenbayev.exception.RecordNotFoundException;
import kz.iitu.itse1910.variant2issenbayev.exception.RecordUndeletableException;
import kz.iitu.itse1910.variant2issenbayev.mapper.UserMapper;
import kz.iitu.itse1910.variant2issenbayev.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public List<UserResp> getUsers(User.Role role) {
        return userRepository.findAllByRole(role).stream()
                .map(UserMapper.INSTANCE::toDto)
                .collect(Collectors.toList());
    }

    public UserResp getUserById(long id) {
        User user = getByIdOrThrow(id);
        return UserMapper.INSTANCE.toDto(user);
    }

    public UserResp registerUser(UserSignupReq signupReq) {
        String email = signupReq.getEmail();
        String username = signupReq.getUsername();

        throwIfAlreadyTaken(email, username);

        User user = UserMapper.INSTANCE.toEntity(signupReq);
        user.setRole(User.Role.SALESMAN);
        user.setPassword(
                passwordEncoder.encode(signupReq.getPassword())
        );

        User savedUser = userRepository.save(user);
        return UserMapper.INSTANCE.toDto(savedUser);
    }

    public void changePassword(long id, UserPasswdChangeReq passwdChangeReq) {
        User user = getByIdOrThrow(id);

        String suppliedPassword = passwdChangeReq.getOldPassword();
        String expectedPassword = user.getPassword();
        if (!passwordEncoder.matches(suppliedPassword, expectedPassword)) {
            throw new InvalidCredentialsException("Invalid password supplied");
        }

        String newPassword = passwdChangeReq.getNewPassword();
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }

    public UserResp updateUser(long id, UserUpdateReq userUpdateReq) {
        User user = getByIdOrThrow(id);

        String newEmail = userUpdateReq.getEmail();
        String newUsername = userUpdateReq.getUsername();
        newEmail = user.getEmail().equals(newEmail) ? null : newEmail;
        newUsername = user.getUsername().equals(newUsername) ? null : newUsername;
        if (newEmail != null || newUsername != null) {
            throwIfAlreadyTaken(newEmail, newUsername);
        }

        UserMapper.INSTANCE.updateEntityFromDto(user, userUpdateReq);
        User updatedUser = userRepository.save(user);
        return UserMapper.INSTANCE.toDto(updatedUser);
    }

    public void deleteUser(long id) {
        User user = getByIdOrThrow(id);

        String errMsg = "";
        if (user.isAdmin()) {
            errMsg = "Admin cannot be deleted";
        } else if (user.hasAssociatedTransactions()) {
            errMsg = "User " + user.getUsername() + " cannot be deleted " +
                    "since there are transactions associated with them.";
        }
        if (!errMsg.isEmpty()) {
            throw new RecordUndeletableException(errMsg);
        }

        userRepository.delete(user);
    }

    private User getByIdOrThrow(long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("User with id " + id + " does not exist."));
    }

    private void throwIfAlreadyTaken(String email, String username) {
        List<String> errDetails = new ArrayList<>();
        if (userRepository.findByEmail(email).isPresent()) {
            errDetails.add("Email " + email + " is already taken");
        }
        if (userRepository.findByUsername(username).isPresent()) {
            errDetails.add("Username " + username + " is already taken");
        }
        if (!errDetails.isEmpty()) {
            throw new RecordAlreadyExistsException(errDetails);
        }
    }
}
