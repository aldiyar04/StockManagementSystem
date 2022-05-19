package kz.iitu.itse1910.variant2issenbayev.service;

import kz.iitu.itse1910.variant2issenbayev.dto.request.UserUpdateReq;
import kz.iitu.itse1910.variant2issenbayev.entity.User;
import kz.iitu.itse1910.variant2issenbayev.exception.RecordAlreadyExistsException;
import kz.iitu.itse1910.variant2issenbayev.repository.UserRepository;
import kz.iitu.itse1910.variant2issenbayev.testdata.UserData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatNoException;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.when;

class UserServiceTest {
    @Mock
    UserRepository userRepository;
    @InjectMocks
    UserService underTest;

    UserData userData = new UserData();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void updateUser_shouldNotThrow_whenNewEmailAndUsernameSameAsOldOnes() {
        // arrange
        User admin = userData.getAdmin();
        long id = admin.getId();
        when(userRepository.findById(id)).thenReturn(Optional.of(admin));

        String email = admin.getEmail();
        String username = admin.getUsername();

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(admin));
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(admin));

        // act & assert
        assertThatNoException().isThrownBy(() -> underTest.updateUser(id, updateReq(email, null)));
        assertThatNoException().isThrownBy(() -> underTest.updateUser(id, updateReq(null, username)));
        assertThatNoException().isThrownBy(() -> underTest.updateUser(id, updateReq(email, username)));
    }

    @Test
    void updateUser_shouldThrow_whenNewEmailAndUsernameAlreadyTakenByAnotherUser() {
        long id = arrangeForUpdateUser();

        // arrange
        User anotherUser = userData.getSalesman1();
        String anotherEmail = anotherUser.getEmail();
        String anotherUsername = anotherUser.getUsername();

        when(userRepository.findByEmail(anotherEmail)).thenReturn(Optional.of(anotherUser));
        when(userRepository.findByUsername(anotherUsername)).thenReturn(Optional.of(anotherUser));

        // act & assert
        assertThatThrownBy(() ->
                underTest.updateUser(id, updateReq(anotherEmail, null))
        ).isInstanceOf(RecordAlreadyExistsException.class);
        assertThatThrownBy(() ->
                underTest.updateUser(id, updateReq(null, anotherUsername))
        ).isInstanceOf(RecordAlreadyExistsException.class);
        assertThatThrownBy(() ->
                underTest.updateUser(id, updateReq(anotherEmail, anotherUsername))
        ).isInstanceOf(RecordAlreadyExistsException.class);
    }

    @Test
    void updateUser_shouldNotThrow_whenNewEmailAndUsernameNonTaken() {
        long id = arrangeForUpdateUser();

        // arrange
        String nonTakenEmail = "nontaken@email.com";
        String nonTakenUsername = "non_taken_username";

        when(userRepository.findByEmail(nonTakenEmail)).thenReturn(Optional.empty());
        when(userRepository.findByUsername(nonTakenUsername)).thenReturn(Optional.empty());


        // act & assert
        assertThatNoException().isThrownBy(() ->
                underTest.updateUser(id, updateReq(nonTakenEmail, null))
        );
        assertThatNoException().isThrownBy(() ->
                underTest.updateUser(id, updateReq(null, nonTakenUsername))
        );
        assertThatNoException().isThrownBy(() ->
                underTest.updateUser(id, updateReq(nonTakenEmail, nonTakenUsername))
        );
    }

    private long arrangeForUpdateUser() {
        User admin = userData.getAdmin();
        long id = admin.getId();
        when(userRepository.findById(id)).thenReturn(Optional.of(admin));
        return id;
    }

    private UserUpdateReq updateReq(String email, String username) {
        return UserUpdateReq.builder()
                .email(email)
                .username(username)
                .build();
    }

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
//    void testChangePassword() {
//        underTest.changePassword(0L, new UserPasswdChangeReq("oldPassword", "newPassword"));
//    }
//
//    @Test
//    void testDeleteUser() {
//        underTest.deleteUser(0L);
//    }
}