//package kz.iitu.itse1910.variant2issenbayev.controller;
//
//import kz.iitu.itse1910.variant2issenbayev.dto.UserDto;
//import kz.iitu.itse1910.variant2issenbayev.dto.request.UserLoginReq;
//import kz.iitu.itse1910.variant2issenbayev.dto.request.UserPasswdChangeReq;
//import kz.iitu.itse1910.variant2issenbayev.dto.request.UserSignupReq;
//import kz.iitu.itse1910.variant2issenbayev.dto.request.UserUpdateReq;
//import kz.iitu.itse1910.variant2issenbayev.entity.User;
//import kz.iitu.itse1910.variant2issenbayev.service.UserService;
//import lombok.AllArgsConstructor;
//import org.springframework.http.HttpStatus;
//import org.springframework.web.bind.annotation.DeleteMapping;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PatchMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.PutMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.ResponseStatus;
//import org.springframework.web.bind.annotation.RestController;
//
//import javax.validation.Valid;
//import java.util.List;
//
//@RestController
//@RequestMapping("/users")
//@AllArgsConstructor
//public class UserController {
//    private final UserService userService;
//
//    @GetMapping
//    public List<UserDto> getUsers(@RequestParam User.Role role) {
//        return userService.getUsers(role);
//    }
//
//    @GetMapping("/{id}")
//    public UserDto getUserById(@PathVariable("id") long id) {
//        return userService.getUserById(id);
//    }
//
//    @PostMapping
//    public UserDto registerUser(@Valid @RequestBody UserSignupReq signupReq) {
//        return userService.registerUser(signupReq);
//    }
//
//    @PostMapping("/login")
//    public void authenticateUser(@Valid @RequestBody UserLoginReq loginReq) {
//        userService.authenticateUser(loginReq);
//    }
//
//    @PatchMapping("/{id}")
//    public void changePassword(@PathVariable("id") long id, @Valid @RequestBody UserPasswdChangeReq passwdChangeReq) {
//        userService.changePassword(id, passwdChangeReq);
//    }
//
//    @PutMapping("/{id}")
//    public UserDto updateUser(@PathVariable("id") long id, @Valid @RequestBody UserUpdateReq userUpdateReq) {
//        return userService.updateUser(id, userUpdateReq);
//    }
//
//    @DeleteMapping("/{id}")
//    @ResponseStatus(HttpStatus.NO_CONTENT)
//    public void deleteUser(@PathVariable("id") long id) {
//        userService.deleteUser(id);
//    }
//}
