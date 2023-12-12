package com.example.demo.service;


import com.example.demo.dto.request.JwtRequest;
import com.example.demo.dto.request.RegistrationUserDto;
import com.example.demo.dto.request.UserDto;
import com.example.demo.entity.User;
import com.example.demo.exceptions.AppError;
import com.example.demo.repository.UserRepository;
import com.example.demo.utils.JwtTokenUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
public class UserRegistrationTest {

    @Autowired
    private AuthService authService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

//    @BeforeEach
//    @AfterEach
//    public void clear() {
//        userRepository.deleteAll();
//    }

    @Test
    public void createUserNotRegister() {
        JwtRequest newUser = new JwtRequest();
        newUser.setUsername("test1");
        newUser.setPassword("test2");

        authService.createAuthToken(newUser);

        ResponseEntity<?> response = authService.createAuthToken(newUser);
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertTrue(response.getBody() instanceof AppError);
        AppError error = (AppError) response.getBody();
        assertEquals(HttpStatus.UNAUTHORIZED.value(), error.getStatus());
        assertEquals("Неправильный логин или пароль", error.getMessage());
    }
    @Test
    public void createUserRegister() {
        UserRepository userRepositoryMock = mock(UserRepository.class);
        UserService userServiceMock = mock(UserService.class);
        JwtTokenUtils jwtTokenUtilsMock = mock(JwtTokenUtils.class);
        AuthenticationManager authenticationManagerMock = mock(AuthenticationManager.class);

        AuthService authService = new AuthService(userServiceMock, jwtTokenUtilsMock, authenticationManagerMock);

        when(userServiceMock.createUser(any(RegistrationUserDto.class))).thenReturn(new User());

        RegistrationUserDto registrationUserDto = new RegistrationUserDto();
        registrationUserDto.setUsername("Ivan");
        registrationUserDto.setEmail("ivan228@gmail.com");
        registrationUserDto.setGender("мужчина");
        registrationUserDto.setPassword("12345");
        registrationUserDto.setConfirmPassword("12345");

        ResponseEntity<?> firstResponse = authService.createNewUser(registrationUserDto);

        assertEquals(HttpStatus.OK, firstResponse.getStatusCode());
        assertTrue(firstResponse.getBody() instanceof UserDto);
    }

    @Test
    public void createUserMismatchPassword() {
        RegistrationUserDto registrationUserDto = new RegistrationUserDto();
        registrationUserDto.setUsername("Ivan");
        registrationUserDto.setEmail("ivan228@gmail.com");
        registrationUserDto.setGender("мужчина");
        registrationUserDto.setPassword("12345");
        registrationUserDto.setConfirmPassword("123456");

        authService.createNewUser(registrationUserDto);

        ResponseEntity<?> response = authService.createNewUser(registrationUserDto);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(response.getBody() instanceof AppError);
        AppError error = (AppError) response.getBody();

        assertEquals("Пароли не совпадают", error.getMessage());
        assertTrue(userRepository.findByName("Ivan").isEmpty());

    }
}
