package com.example.demo.controller;

import com.example.demo.dto.request.UserGenderDto;
import com.example.demo.dto.response.FileResponseDto;
import com.example.demo.dto.response.UserAccountResponseDto;
import com.example.demo.dto.response.UserResponseDto;
import com.example.demo.entity.User;
import com.example.demo.exceptions.handlers.UserNotFoundError;
import com.example.demo.mapper.FileDtoMapper;
import com.example.demo.mapper.UserMapper;
import com.example.demo.service.FileService;
import com.example.demo.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;
    private final FileService fileService;
    private final FileDtoMapper photoMapper;
    private final FileDtoMapper mapper;

    @GetMapping("/find_user_by_id/{id}")
    public String getUserNameById(@PathVariable @Valid Long id) {
        return userService.findById(id).getName();
    }

    @GetMapping("/find_all_users")
    public List<UserResponseDto> getAllUser() {
        return userService.getAllUsers()
                .stream()
                .map(userMapper::fromModelToDto)
                .toList();
    }

    @DeleteMapping("/ban/{id}")
    public void banById(@PathVariable @Valid Long id) {
        userService.deleteUser(id);
    }

    @PostMapping("/unban/{id}")
    public void unbanById(@PathVariable @Valid Long id) {
        userService.unbanUser(id);
    }

    @PostMapping(value = "/save", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public FileResponseDto saveFile(@RequestPart MultipartFile request, long postId) throws IOException {
        var savedFile = fileService.savePhoto(request.getBytes(), postId);
        return mapper.entityToResponse(savedFile.getPhoto());
    }

    @GetMapping("/get_photo/{id}")
    public FileResponseDto getUserPhoto(@PathVariable("id") Long id) {
        return photoMapper.entityToResponse(fileService.getPhoto(id));
    }

    @PostMapping("/add_gender")
    public User addGender(@RequestBody UserGenderDto userGenderDto) {
        return userService.addGender(userGenderDto);
    }

    @GetMapping("/get_user/{userId}")
    public String getNameByUserId(@PathVariable("userId") Long id) {
        return userService.findById(id).getName();
    }

    @GetMapping("/get_gender/{userId}")
    public String getGenderByUserId(@PathVariable("userId") long id) {
        return userService.findById(id).getGender();
    }

    @GetMapping("/get_cabinet/{userId}")
    public UserAccountResponseDto getUserAccount(@PathVariable("userId") Long id) {
        return userService.getUserAccountById(id);
    }

    @GetMapping("/get_username/{username}")
    public Long getIdByUsername(@PathVariable String username) throws UserNotFoundError {
        return userService.getUserByName(username);
    }
}
