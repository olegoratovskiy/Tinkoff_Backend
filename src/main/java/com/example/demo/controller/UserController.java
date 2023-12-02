package com.example.demo.controller;

import com.example.demo.dto.request.UserGenderDto;
import com.example.demo.dto.response.FileResponseDto;
import com.example.demo.dto.response.PhotoResponseDto;
import com.example.demo.dto.response.UserCabinetResponseDto;
import com.example.demo.dto.response.UserResponseDto;
import com.example.demo.entity.User;
import com.example.demo.exceptions.handlers.UserNotFoundError;
import com.example.demo.mapper.FileDtoMapper;
import com.example.demo.mapper.PhotoDtoMapper;
import com.example.demo.service.FileService;
import com.example.demo.dto.request.UserGenderDto;
import com.example.demo.dto.response.FileResponseDto;
import com.example.demo.dto.response.UserAccountResponseDto;
import com.example.demo.entity.User;
import com.example.demo.exceptions.handlers.UserNotFoundError;
import com.example.demo.mapper.FileDtoMapper;
import com.example.demo.service.FileService;
import com.example.demo.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final FileService fileService;
    private final FileDtoMapper photoMapper;
    private final FileDtoMapper mapper;

    @GetMapping("/unsecured")
    public String unsecuredData() {
        return "Unsecured data";
    }

    @GetMapping("/find_user_by_id/{id}")
    public String getUserNameById(@PathVariable @Valid Long id) {
        return userService.findById(id).getName();
    }

    @GetMapping("/secured")
    public String securedData() {
        return "Secured data";
    }

    @GetMapping("/admin")
    public String adminData() {
        return "Admin data";
    }

    @GetMapping("/info")
    public String userData(Principal principal) {
        return principal.getName();
    }

    @PostMapping(value = "save", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
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
    public String getGenderByUserId(@PathVariable("userId") Long id) {
        return userService.findById(id).getGender();
    }

    @GetMapping("/get_cabinet/{userId}")
    public UserAccountResponseDto getUserAccount(@PathVariable("userId") Long id) {
        return userService.getUserAccountById(id);
    }

    @GetMapping("/get_username/{username}")
    public Long getIdByUsername(@PathVariable("username") String username) throws UserNotFoundError {
        return userService.getUserByName(username);
    }
}
