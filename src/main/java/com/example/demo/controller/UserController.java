package com.example.demo.controller;

import com.example.demo.dto.request.UserChangeNameDto;
import com.example.demo.dto.request.UserGenderDto;
import com.example.demo.dto.response.FileResponseDto;
import com.example.demo.dto.response.UserAccountResponseDto;
import com.example.demo.dto.response.UserResponseDto;
import com.example.demo.exceptions.handlers.ModeratorUserExist;
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

    @GetMapping("/get/{id}")
    public UserAccountResponseDto getUserNameById(@PathVariable @Valid Long id) {
        return userService.getUserAccountById(id);
    }

    @GetMapping("/get/all")
    public List<UserResponseDto> getAllUser() {
        return userService.getAllUsers()
                .stream()
                .map(userMapper::fromModelToDto)
                .toList();
    }

    @GetMapping("/get")
    public Long getIdByUsername(@RequestParam String username) throws UserNotFoundError {
        return userService.getUserByName(username);
    }

    @DeleteMapping("/ban/{userId}")
    public void banById(@PathVariable @Valid Long userId) {
        userService.deleteUser(userId);
    }

    @PostMapping("/unban/{userId}")
    public void unbanById(@PathVariable @Valid Long userId) {
        userService.unbanUser(userId);
    }

    @PostMapping("/give_moderator/{userId}")
    public void giveModerator(@PathVariable @Valid Long userId) throws ModeratorUserExist {
        userService.giveModerator(userId);
    }

    @PostMapping("/take_moderator/{userId}")
    public void takeModerator(@PathVariable @Valid Long userId) throws ModeratorUserExist {
        userService.takeModerator(userId);
    }
    @PostMapping(value = "/photo/add", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public FileResponseDto saveFile(@RequestPart MultipartFile request, Long userId) throws IOException {
        var savedFile = fileService.savePhoto(request.getBytes(), userId);
        return mapper.entityToResponse(savedFile.getPhoto());
    }

    @GetMapping("/photo/get")
    public FileResponseDto getUserPhoto(@RequestParam Long userId) {
        return photoMapper.entityToResponse(fileService.getPhoto(userId));
    }

    @PostMapping("/gender/add")
    public UserAccountResponseDto addGender(@RequestBody UserGenderDto userGenderDto) {
        userService.addGender(userGenderDto);
        return userService.getUserAccountById(userGenderDto.getUserId());
    }

    @PostMapping("/name/change")
    public UserAccountResponseDto addGender(@RequestBody UserChangeNameDto userChangeNameDto) {
        userService.changeName(userChangeNameDto);
        return userService.getUserAccountById(userChangeNameDto.getUserId());
    }


}
