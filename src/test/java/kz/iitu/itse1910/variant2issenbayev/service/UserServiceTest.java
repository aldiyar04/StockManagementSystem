//package kz.iitu.itse1910.variant2issenbayev.service;
//
//import kz.iitu.itse1910.variant2issenbayev.dto.request.UserLoginReq;
//import kz.iitu.itse1910.variant2issenbayev.dto.request.UserPasswdChangeReq;
//import kz.iitu.itse1910.variant2issenbayev.dto.response.UserResp;
//import kz.iitu.itse1910.variant2issenbayev.entity.Transaction;
//import kz.iitu.itse1910.variant2issenbayev.entity.User;
//import kz.iitu.itse1910.variant2issenbayev.repository.UserRepository;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//
//import java.time.LocalDate;
//import java.time.Month;
//import java.util.List;
//
//import static org.mockito.Mockito.*;
//
//class UserServiceTest {
//    @Mock
//    UserRepository userRepository;
//    @InjectMocks
//    UserService underTest;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//    @Test
//    void testGetUsers() {
//        when(userRepository.findAllByRole(any())).thenReturn(List.of(new User(Long.valueOf(1), new User(Long.valueOf(1), new User(Long.valueOf(1), new User(Long.valueOf(1), new User(Long.valueOf(1), new User(Long.valueOf(1), new User(Long.valueOf(1), new User(Long.valueOf(1), new User(Long.valueOf(1), new User(Long.valueOf(1), null.kz.iitu.itse1910.variant2issenbayev.entity.User.Role.ADMIN, "email", "username", "password", "firstName", "lastName", LocalDate.of(2022, Month.MAY, 14), List.of(null)).kz.iitu.itse1910.variant2issenbayev.entity.User.Role.ADMIN, "email", "username", "password", "firstName", "lastName", LocalDate.of(2022, Month.MAY, 14), List.of(new Transaction())).kz.iitu.itse1910.variant2issenbayev.entity.User.Role.ADMIN, "email", "username", "password", "firstName", "lastName", LocalDate.of(2022, Month.MAY, 14), List.of(new Transaction())).kz.iitu.itse1910.variant2issenbayev.entity.User.Role.ADMIN, "email", "username", "password", "firstName", "lastName", LocalDate.of(2022, Month.MAY, 14), List.of(new Transaction())).kz.iitu.itse1910.variant2issenbayev.entity.User.Role.ADMIN, "email", "username", "password", "firstName", "lastName", LocalDate.of(2022, Month.MAY, 14), List.of(new Transaction())).kz.iitu.itse1910.variant2issenbayev.entity.User.Role.ADMIN, "email", "username", "password", "firstName", "lastName", LocalDate.of(2022, Month.MAY, 14), List.of(new Transaction())).kz.iitu.itse1910.variant2issenbayev.entity.User.Role.ADMIN, "email", "username", "password", "firstName", "lastName", LocalDate.of(2022, Month.MAY, 14), List.of(new Transaction())).kz.iitu.itse1910.variant2issenbayev.entity.User.Role.ADMIN, "email", "username", "password", "firstName", "lastName", LocalDate.of(2022, Month.MAY, 14), List.of(new Transaction())).kz.iitu.itse1910.variant2issenbayev.entity.User.Role.ADMIN, "email", "username", "password", "firstName", "lastName", LocalDate.of(2022, Month.MAY, 14), List.of(new Transaction())).kz.iitu.itse1910.variant2issenbayev.entity.User.Role.ADMIN, "email", "username", "password", "firstName", "lastName", LocalDate.of(2022, Month.MAY, 14), List.of(new Transaction()))));
//
//        List<UserResp> result = underTest.getUsers(new User(Long.valueOf(1), new User(Long.valueOf(1), new User(Long.valueOf(1), new User(Long.valueOf(1), new User(Long.valueOf(1), new User(Long.valueOf(1), new User(Long.valueOf(1), new User(Long.valueOf(1), new User(Long.valueOf(1), null.kz.iitu.itse1910.variant2issenbayev.entity.User.Role.ADMIN, "email", "username", "password", "firstName", "lastName", LocalDate.of(2022, Month.MAY, 14), List.of(null)).kz.iitu.itse1910.variant2issenbayev.entity.User.Role.ADMIN, "email", "username", "password", "firstName", "lastName", LocalDate.of(2022, Month.MAY, 14), List.of(new Transaction())).kz.iitu.itse1910.variant2issenbayev.entity.User.Role.ADMIN, "email", "username", "password", "firstName", "lastName", LocalDate.of(2022, Month.MAY, 14), List.of(new Transaction())).kz.iitu.itse1910.variant2issenbayev.entity.User.Role.ADMIN, "email", "username", "password", "firstName", "lastName", LocalDate.of(2022, Month.MAY, 14), List.of(new Transaction())).kz.iitu.itse1910.variant2issenbayev.entity.User.Role.ADMIN, "email", "username", "password", "firstName", "lastName", LocalDate.of(2022, Month.MAY, 14), List.of(new Transaction())).kz.iitu.itse1910.variant2issenbayev.entity.User.Role.ADMIN, "email", "username", "password", "firstName", "lastName", LocalDate.of(2022, Month.MAY, 14), List.of(new Transaction())).kz.iitu.itse1910.variant2issenbayev.entity.User.Role.ADMIN, "email", "username", "password", "firstName", "lastName", LocalDate.of(2022, Month.MAY, 14), List.of(new Transaction())).kz.iitu.itse1910.variant2issenbayev.entity.User.Role.ADMIN, "email", "username", "password", "firstName", "lastName", LocalDate.of(2022, Month.MAY, 14), List.of(new Transaction())).kz.iitu.itse1910.variant2issenbayev.entity.User.Role.ADMIN, "email", "username", "password", "firstName", "lastName", LocalDate.of(2022, Month.MAY, 14), List.of(new Transaction())).kz.iitu.itse1910.variant2issenbayev.entity.User.Role.ADMIN);
//        Assertions.assertEquals(List.of(null), result);
//    }
//
//    @Test
//    void testGetUserById() {
//        when(userRepository.findById(anyLong())).thenReturn(null);
//
//        UserResp result = underTest.getUserById(0L);
//        Assertions.assertEquals(null, result);
//    }
//
//    @Test
//    void testRegisterUser() {
//        UserResp result = underTest.registerUser(null);
//        Assertions.assertEquals(null, result);
//    }
//
//    @Test
//    void testAuthenticateUser() {
//        underTest.authenticateUser(new UserLoginReq("username", "password"));
//    }
//
//    @Test
//    void testChangePassword() {
//        underTest.changePassword(0L, new UserPasswdChangeReq("oldPassword", "newPassword"));
//    }
//
//    @Test
//    void testUpdateUser() {
//        UserResp result = underTest.updateUser(0L, null);
//        Assertions.assertEquals(null, result);
//    }
//
//    @Test
//    void testDeleteUser() {
//        underTest.deleteUser(0L);
//    }
//}