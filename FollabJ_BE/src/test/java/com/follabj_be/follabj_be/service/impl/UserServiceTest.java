package com.follabj_be.follabj_be.service.impl;

import com.follabj_be.follabj_be.config.securityConfig.PasswordEncoder;
import com.follabj_be.follabj_be.dto.AppUserDTO;
import com.follabj_be.follabj_be.dto.PasswordDTO;
import com.follabj_be.follabj_be.dto.UpdateUserDTO;
import com.follabj_be.follabj_be.dto.UserDTO;
import com.follabj_be.follabj_be.entity.*;
import com.follabj_be.follabj_be.errorMessge.CustomErrorMessage;
import com.follabj_be.follabj_be.repository.InvitationRepository;
import com.follabj_be.follabj_be.repository.UserRepository;
import com.follabj_be.follabj_be.service.TokenInterface;
import org.hibernate.ObjectNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("UserService test")
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private TokenInterface tokenInterface;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private InvitationRepository invitationRepository;
    @InjectMocks
    private UserService userService;

    @Test
    @DisplayName("Should generate a random number within the given range")
    void randomNumberWithinRange() {
        int min = 0;
        int max = 10;
        int randomNumber = UserService.randomNumber(min, max);

        assertTrue(
                randomNumber >= min && randomNumber <= max,
                "Random number should be within the given range");
    }

    @Test
    @DisplayName("Should generate a random string with the correct length and format")
    void randomStringGeneration() { // Call the private method "random" using ReflectionTestUtils
        String randomString = (String) ReflectionTestUtils.invokeMethod(userService, "random");

        // Check if the generated string has the correct length
        assertEquals(8, randomString.length());

        // Check if the generated string contains only allowed characters
        String allowedCharacters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        for (char ch : randomString.toCharArray()) {
            assertTrue(allowedCharacters.contains(String.valueOf(ch)));
        }
    }

    @Test
    @DisplayName("Should generate different random strings on multiple calls")
    void randomStringVariability() {
        String randomString1 = (String) ReflectionTestUtils.invokeMethod(userService, "random");
        String randomString2 = (String) ReflectionTestUtils.invokeMethod(userService, "random");
        String randomString3 = (String) ReflectionTestUtils.invokeMethod(userService, "random");

        assertNotEquals(randomString1, randomString2, "Random strings should be different");
        assertNotEquals(randomString1, randomString3, "Random strings should be different");
        assertNotEquals(randomString2, randomString3, "Random strings should be different");
    }

    @Test
    @DisplayName("Should throw ObjectNotFoundException when user id is not found")
    void getUserProfileWhenUserIdNotFoundThenThrowException() {
        Long userId = 1L;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(ObjectNotFoundException.class, () -> userService.getUserProfile(userId));
        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    @DisplayName("Should return user profile when user id is valid")
    void getUserProfileWhenUserIdIsValid() {
        Long userId = 1L;
        AppUser appUser = new AppUser();
        appUser.setId(userId);
        appUser.setEmail("test@example.com");
        appUser.setUsername("testUser");
        appUser.setStatus(1);
        appUser.setFullname("Test User");
        appUser.setPhone_number("1234567890");

        when(userRepository.findById(userId)).thenReturn(Optional.of(appUser));

        AppUserDTO result = userService.getUserProfile(userId);

        assertNotNull(result);
        assertEquals(userId, result.getId());
        assertEquals(appUser.getEmail(), result.getEmail());
        assertEquals(appUser.getUsername(), result.getUsername());
        assertEquals(appUser.getStatus(), result.getStatus());
        assertEquals(appUser.getFullname(), result.getFullname());
        assertEquals(appUser.getPhone_number(), result.getPhone_number());

        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    @DisplayName("Should throw an exception when the email does not exist")
    void forgetPasswordWhenEmailDoesNotExistThenThrowException() {
        String email = "test@example.com";
        when(userRepository.findAppUserByEmail(email)).thenReturn(Optional.empty());

        assertThrows(ObjectNotFoundException.class, () -> userService.forgetPassword(email));

        verify(userRepository, times(1)).findAppUserByEmail(email);
    }

    @Test
    @DisplayName("Should return the count of users by day")
    void countUsersByDay() {
        String by = "day";
        String expectedCount = "5";
        when(userRepository.countByDay(anyInt())).thenReturn(expectedCount);

        String actualCount = userService.count(by);

        assertEquals(expectedCount, actualCount);
        verify(userRepository).countByDay(anyInt());
    }

    @Test
    @DisplayName("Should return the count of users by month")
    void countUsersByMonth() {
        String by = "MONTH";
        String expectedCount = "5";
        when(userRepository.countByMonth(anyInt())).thenReturn(expectedCount);

        String actualCount = userService.count(by);

        assertEquals(expectedCount, actualCount);
        verify(userRepository, times(1)).countByMonth(anyInt());
    }

    @Test
    @DisplayName("Should return the count of users by year")
    void countUsersByYear() {
        String by = "YEAR";
        String expectedCount = "5";
        when(userRepository.countByYear(anyInt())).thenReturn(expectedCount);

        String actualCount = userService.count(by);

        assertEquals(expectedCount, actualCount);
        verify(userRepository, times(1)).countByYear(anyInt());
        verify(userRepository, never()).countByMonth(anyInt());
        verify(userRepository, never()).countByDay(anyInt());
    }

    @Test
    @DisplayName("Should return 'Wrong format' when the input format is incorrect")
    void countUsersWithWrongFormat() {
        String by = "wrongFormat";
        String expected = "Wrong format";

        //when(userRepository.countByYear(anyInt())).thenReturn("0");
        //when(userRepository.countByMonth(anyInt())).thenReturn("0");
        //when(userRepository.countByDay(anyInt())).thenReturn("0");

        String result = userService.count(by);

        assertEquals(expected, result);
        verify(userRepository, times(0)).countByYear(anyInt());
        verify(userRepository, times(0)).countByMonth(anyInt());
        verify(userRepository, times(0)).countByDay(anyInt());
    }

    @Test
    @DisplayName("Should not update the user when the phone number is already registered")
    void updateUserWhenPhoneNumberIsAlreadyRegistered() {
        UpdateUserDTO updateUserDTO = new UpdateUserDTO(1L, "John Doe", "john_doe", "1234567890");
        AppUser existingUser = new AppUser(1L, "john_doe", "john@example.com");
        AppUser userWithSamePhoneNumber = new AppUser(2L, "jane_doe", "jane@example.com");
        userWithSamePhoneNumber.setPhone_number("1234567890");

        when(userRepository.findById(1L)).thenReturn(Optional.of(existingUser));
        when(userRepository.findAppUserByPhone_number("1234567890"))
                .thenReturn(Optional.of(userWithSamePhoneNumber));

        var result = userService.updateUser(updateUserDTO, 1L);

        assertEquals("500", result.get("status"));
        assertEquals("This phone number is already registered", result.get("message"));
        verify(userRepository, never()).save(any(AppUser.class));
    }

    @Test
    @DisplayName("Should not update the user when the user ID does not match the request user ID")
    void updateUserWhenUserIdDoesNotMatchRequestId() {
        UpdateUserDTO updateUserDTO = new UpdateUserDTO();
        updateUserDTO.setU_id(1L);
        updateUserDTO.setFullname("New Fullname");
        updateUserDTO.setUsername("New Username");
        updateUserDTO.setPhone_number("1234567890");

        Long u_id = 2L;

        AppUser appUser = new AppUser();
        appUser.setId(u_id);
        appUser.setFullname("Old Fullname");
        appUser.setUsername("Old Username");
        appUser.setPhone_number("0987654321");

        when(userRepository.findById(u_id)).thenReturn(Optional.of(appUser));

        Map<String, String> result = userService.updateUser(updateUserDTO, u_id);

        assertEquals("401", result.get("status"));
        assertEquals(CustomErrorMessage.NO_PERMISSION.getMessage(), result.get("message"));
        verify(userRepository, times(1)).findById(u_id);
        verify(userRepository, times(0)).save(any(AppUser.class));
    }

    @Test
    @DisplayName("Should update the user when the phone number is not registered")
    void updateUserWhenPhoneNumberIsNotRegistered() {
        UpdateUserDTO updateUserDTO = new UpdateUserDTO(1L, "John Doe", "johndoe", "1234567890");
        AppUser appUser =
                new AppUser(
                        "johndoe",
                        "john@example.com",
                        "password",
                        "John Doe",
                        "0987654321",
                        "2022-01-01",
                        1,
                        Collections.emptySet());
        when(userRepository.findById(1L)).thenReturn(Optional.of(appUser));
        when(userRepository.findAppUserByPhone_number("1234567890")).thenReturn(Optional.empty());
        when(userRepository.save(any(AppUser.class))).thenReturn(appUser);

        Map<String, String> result = userService.updateUser(updateUserDTO, 1L);

        assertEquals("200", result.get("status"));
        assertEquals("Update success", result.get("message"));
        verify(userRepository, times(1)).findById(1L);
        verify(userRepository, times(1)).findAppUserByPhone_number("1234567890");
        verify(userRepository, times(1)).save(any(AppUser.class));
    }

    @Test
    @DisplayName("Should not change the password when the user does not have permission")
    void changePasswordWhenUserDoesNotHavePermission() {
        PasswordDTO passwordDTO = new PasswordDTO(2, "new_password", "old_password");
        Long u_id = 1L;

        AppUser appUser = new AppUser();
        appUser.setId(u_id);
        appUser.setPassword("old_password");

        when(userRepository.findById(u_id)).thenReturn(Optional.of(appUser));

        String result = userService.changePassword(passwordDTO, u_id);

        assertEquals(CustomErrorMessage.NO_PERMISSION.getMessage(), result);
        verify(userRepository, times(1)).findById(u_id);
        verify(passwordEncoder, never()).bCryptPasswordEncoder();
        verify(userRepository, never()).save(any(AppUser.class));
    }

    @Test
    @DisplayName("Should not update the status of the user when the status is invalid")
    void updateStatusWhenStatusIsInvalid() {
        Long userId = 1L;
        int invalidStatus = 3;

        AppUserDTO result = userService.updateStatus(invalidStatus, userId);

        assertNull(result);
        verify(userRepository, times(0)).findById(userId);
        verify(userRepository, times(0)).save(any(AppUser.class));
    }

    @Test
    @DisplayName("Should throw an ObjectNotFoundException when the user is not found")
    void updateStatusWhenUserNotFoundThrowsObjectNotFoundException() {
        int status = 1;
        Long u_id = 1L;
        when(userRepository.findById(u_id)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(ObjectNotFoundException.class, () -> userService.updateStatus(status, u_id));
        verify(userRepository, times(1)).findById(u_id);
    }

    @Test
    @DisplayName("Should update the status of the user when the status is valid")
    void updateStatusWhenStatusIsValid() {
        Long userId = 1L;
        int status = 1;
        AppUser appUser = new AppUser();
        appUser.setId(userId);
        appUser.setEmail("test@example.com");
        appUser.setUsername("testUser");
        appUser.setStatus(0);

        AppUserDTO expectedAppUserDTO =
                new AppUserDTO(userId, "test@example.com", "testUser", status);

        when(userRepository.findById(userId)).thenReturn(Optional.of(appUser));

        AppUserDTO result = userService.updateStatus(status, userId);

        assertEquals(expectedAppUserDTO.getStatus(), result.getStatus());
        verify(userRepository, times(1)).findById(userId);
        verify(userRepository, times(1)).save(appUser);
    }

    @Test
    @DisplayName("Should return an AppUserDTO with updated status when the status is updated")
    void updateStatusReturnsAppUserDTOWithUpdatedStatus() {
        Long userId = 1L;
        int newStatus = 2;
        AppUser appUser = new AppUser();
        appUser.setId(userId);
        appUser.setEmail("test@example.com");
        appUser.setUsername("testUser");
        appUser.setStatus(1);

        when(userRepository.findById(userId)).thenReturn(Optional.of(appUser));
        when(userRepository.save(appUser)).thenReturn(appUser);

        AppUserDTO updatedAppUserDTO = userService.updateStatus(newStatus, userId);

        assertNotNull(updatedAppUserDTO);
        assertEquals(userId, updatedAppUserDTO.getId());
        assertEquals("test@example.com", updatedAppUserDTO.getEmail());
        assertEquals("testUser", updatedAppUserDTO.getUsername());
        assertEquals(newStatus, updatedAppUserDTO.getStatus());

        verify(userRepository, times(1)).findById(userId);
        verify(userRepository, times(1)).save(appUser);
    }

    @Test
    @DisplayName("Should return a list of invited users by project ID")
    void getInvitedUserByProjectId() {
        Long projectId = 1L;
        AppUser appUser = new AppUser(1L, "username", "email@example.com");
        List<AppUser> expectedUsers = Collections.singletonList(appUser);

        when(userRepository.findAllUserInvitedToProject(projectId)).thenReturn(expectedUsers);

        List<AppUser> actualUsers = userService.getInvitedUserByProjectId(projectId);

        assertEquals(expectedUsers, actualUsers);
        verify(userRepository, times(1)).findAllUserInvitedToProject(projectId);
    }

    @Test
    @DisplayName("Should return null when the email does not exist")
    void getUserByEmailWhenEmailDoesNotExist() {
        String email = "test@example.com";
        when(userRepository.findByEmail(email)).thenReturn(null);

        AppUser result = userService.getUserByEmail(email);

        assertNull(result);
        verify(userRepository, times(1)).findByEmail(email);
    }

    @Test
    @DisplayName("Should return the user when the email exists")
    void getUserByEmailWhenEmailExists() {
        String email = "test@example.com";
        AppUser appUser = new AppUser(1L, "username", email);
        when(userRepository.findByEmail(email)).thenReturn(appUser);

        AppUser result = userService.getUserByEmail(email);

        assertNotNull(result);
        assertEquals(appUser, result);
        verify(userRepository, times(1)).findByEmail(email);
    }

    @Test
    @DisplayName("Should return all users")
    void getAllUsers() {
        AppUser appUser =
                new AppUser("username", "email@example.com", "password", 1, Collections.emptySet());
        List<AppUser> appUsers = Collections.singletonList(appUser);
        when(userRepository.findAll()).thenReturn(appUsers);

        List<AppUser> result = userService.getAllUsers();

        assertEquals(appUsers, result);
        verify(userRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Should throw an exception when the user is not found")
    void getAllInvitationsWhenUserNotFoundThenThrowException() {
        Long userId = 1L;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(ObjectNotFoundException.class, () -> userService.getAllInvitation(userId));
        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    @DisplayName("Should return all invitations for a given user")
    void getAllInvitationsForGivenUser() {
        Long userId = 1L;
        AppUser appUser = new AppUser();
        appUser.setId(userId);
        appUser.setEmail("test@example.com");
        appUser.setUsername("testUser");
        appUser.setPassword("password");

        List<Invitation> expectedInvitations =
                Collections.singletonList(new Invitation(1L, appUser, new Project(), 0));

        when(userRepository.findById(userId)).thenReturn(Optional.of(appUser));
        when(invitationRepository.findByUserId(userId)).thenReturn(expectedInvitations);

        List<Invitation> actualInvitations = userService.getAllInvitation(userId);

        assertEquals(expectedInvitations, actualInvitations);
        verify(userRepository, times(1)).findById(userId);
        verify(invitationRepository, times(1)).findByUserId(userId);
    }

    @Test
    @DisplayName("Should return an empty list when no users have matching email characters")
    void findUsersByEmailWithNoMatchingCharacters() {
        String emailCharacters = "nonexistent";
        when(userRepository.findByEmailLike(emailCharacters)).thenReturn(Collections.emptyList());

        List<UserDTO> result = userService.findUsersByEmail(emailCharacters);

        assertTrue(result.isEmpty());
        verify(userRepository, times(1)).findByEmailLike(emailCharacters);
    }

    @Test
    @DisplayName("Should return a list of UserDTOs with matching email characters")
    void findUsersByEmailWithMatchingCharacters() {
        String email_cha = "test";
        UserDTO userDTO = new UserDTO(1L, "test@example.com", "testUser");
        List<UserDTO> expectedUserDTOs = Collections.singletonList(userDTO);
        when(userRepository.findByEmailLike(email_cha)).thenReturn(expectedUserDTOs);

        List<UserDTO> actualUserDTOs = userService.findUsersByEmail(email_cha);

        assertEquals(expectedUserDTOs, actualUserDTOs);
        verify(userRepository).findByEmailLike(email_cha);
    }

    @Test
    @DisplayName("Should update the user role to active when given a valid user ID")
    void activeUserWithValidUserId() {
        Long userId = 1L;
        AppUser appUser = new AppUser();
        appUser.setId(userId);
        appUser.setStatus(0);

        //when(userRepository.findById(userId)).thenReturn(Optional.of(appUser));

        userService.activeUser(userId);

        verify(userRepository, times(1)).updateRole(userId, 1);
    }

    @Test
    @DisplayName("Should enable the AppUser with the given email and status")
    void enableAppUserWithGivenEmailAndStatus() {
        String email = "test@example.com";
        int status = 1;
        //when(userRepository.findAppUserByEmail(email)).thenReturn(Optional.of(new AppUser()));

        userService.enableAppUser(email, status);

        verify(userRepository, times(1)).enableAppUser(status, email);
    }

    @Test
    @DisplayName("Should save the confirmation token for a given user and token")
    void saveConfirmationToken() {
        AppUser appUser = new AppUser();
        appUser.setId(1L);
        appUser.setUsername("testUser");
        appUser.setEmail("test@example.com");
        appUser.setPassword("password");
        appUser.setStatus(1);
        appUser.setRoles(Collections.singleton(new Role(1L, "ROLE_USER")));

        String token = "testToken";

        userService.saveConfirmationToken(appUser, token);

        verify(tokenInterface, times(1)).saveConfirmationToken(any(ConfirmToken.class));
    }

    @Test
    @DisplayName(
            "Should throw an exception when the email is already taken and the user is enabled")
    void signUpUserWhenEmailIsAlreadyTakenThenThrowException() {
        AppUser appUser =
                new AppUser(
                        "testUsername",
                        "testEmail@example.com",
                        "testPassword",
                        1,
                        Collections.emptySet());
        when(userRepository.findAppUserByEmail(appUser.getEmail()))
                .thenReturn(Optional.of(appUser));

        // Act and Assert
        assertThrows(IllegalStateException.class, () -> userService.signUpUser(appUser));
        verify(userRepository, never()).save(any(AppUser.class));
        verify(tokenInterface, never()).saveConfirmationToken(any(ConfirmToken.class));
    }

    @Test
    @DisplayName("Should throw UsernameNotFoundException when user with given email is not found")
    void loadUserByUsernameThrowsUsernameNotFoundExceptionWhenUserNotFound() {
        String email = "test@example.com";
        when(userRepository.findAppUserByEmail(email)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(UsernameNotFoundException.class, () -> userService.loadUserByUsername(email));
        verify(userRepository, times(1)).findAppUserByEmail(email);
    }

    @Test
    @DisplayName("Should load user by email and return UserDetails object with correct authorities")
    void loadUserByUsernameReturnsUserDetailsWithCorrectAuthorities() {
        String email = "test@example.com";
        String password = "password";
        Role role = new Role(1L, "ROLE_USER");
        AppUser appUser = new AppUser("username", email, password, 1, Collections.singleton(role));
        when(userRepository.findAppUserByEmail(email)).thenReturn(Optional.of(appUser));

        UserDetails userDetails = userService.loadUserByUsername(email);

        assertNotNull(userDetails);
        assertEquals(email, userDetails.getUsername());
        assertEquals(password, userDetails.getPassword());
        assertTrue(
                userDetails.getAuthorities().stream()
                        .anyMatch(authority -> authority.getAuthority().equals("ROLE_USER")));
        verify(userRepository, times(1)).findAppUserByEmail(email);
    }
}