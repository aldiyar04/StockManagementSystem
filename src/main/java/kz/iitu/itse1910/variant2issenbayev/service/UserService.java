package kz.iitu.itse1910.variant2issenbayev.service;

import kz.iitu.itse1910.variant2issenbayev.dto.request.UserPasswdChangeReq;
import kz.iitu.itse1910.variant2issenbayev.dto.request.UserSignupReq;
import kz.iitu.itse1910.variant2issenbayev.dto.request.UserUpdateReq;
import kz.iitu.itse1910.variant2issenbayev.dto.response.UserResp;
import kz.iitu.itse1910.variant2issenbayev.entity.User;
import kz.iitu.itse1910.variant2issenbayev.exception.RecordAlreadyExistsException;
import kz.iitu.itse1910.variant2issenbayev.exception.RecordNotFoundException;
import kz.iitu.itse1910.variant2issenbayev.mapper.UserMapper;
import kz.iitu.itse1910.variant2issenbayev.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;

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
        User savedUser = userRepository.save(user);
        return UserMapper.INSTANCE.toDto(savedUser);
    }

    public void changePassword(long id, UserPasswdChangeReq passwdChangeReq) {

    }

    public UserResp updateUser(long id, UserUpdateReq userUpdateReq) {
        return null;
    }

    public void deleteUser(long id) {
        User user = getByIdOrThrow(id);
        userRepository.delete(user);
    }

    private User getByIdOrThrow(long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("User with id " + id + " does not exist"));
    }

    private void throwIfAlreadyTaken(String email, String username) {
        String errMsg = "";
        if (userRepository.findByEmail(email).isPresent()) {
            errMsg += "Email " + email + " is already taken\n";
        }
        if (userRepository.findByUsername(username).isPresent()) {
            errMsg += "Username " + username + " is already taken\n";
        }
        if (StringUtils.isEmpty(errMsg)) {
            throw new RecordAlreadyExistsException(errMsg);
        }
    }
}
