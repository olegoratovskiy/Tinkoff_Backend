package com.example.demo.service;


import com.example.demo.dto.request.JwtRequest;
import com.example.demo.dto.request.RegistrationUserDto;
import com.example.demo.dto.request.UserDto;
import com.example.demo.exceptions.AppError;
import com.example.demo.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ContextConfiguration
public class UserRegistrationTest {

    @Autowired
    private AuthService authService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @BeforeEach
    @AfterEach
    public void clear() {
        userRepository.deleteAll();
    }

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
        RegistrationUserDto registrationUserDto = new RegistrationUserDto();
        registrationUserDto.setUsername("Ivan");
        registrationUserDto.setEmail("ivan228@gmail.com");
        registrationUserDto.setGender("мужчина");
        registrationUserDto.setPassword("12345");
        registrationUserDto.setConfirmPassword("12345");

        ResponseEntity<?> firstResponse = authService.createNewUser(registrationUserDto);

        assertEquals(HttpStatus.OK, firstResponse.getStatusCode());
        assertTrue(firstResponse.getBody() instanceof UserDto);


        assertTrue(userRepository.findByName("Ivan").isPresent());

        ResponseEntity<?> secondResponse = authService.createNewUser(registrationUserDto);


        assertEquals(HttpStatus.BAD_REQUEST, secondResponse.getStatusCode());
        assertTrue(secondResponse.getBody() instanceof AppError);

        assertTrue(userRepository.findByName("Ivan").isPresent());

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
