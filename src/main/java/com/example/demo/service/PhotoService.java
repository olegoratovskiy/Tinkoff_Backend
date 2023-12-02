package com.example.demo.service;

import com.example.demo.entity.File;
import com.example.demo.entity.User;
import com.example.demo.enums.FileType;
import com.example.demo.repository.FileRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class PhotoService {

    private final FileRepository fileRepository;

    private final UserRepository userRepository;

    public PhotoService(FileRepository fileRepository, UserRepository userRepository) {
        this.fileRepository = fileRepository;
        this.userRepository = userRepository;
    }

    public User savePhoto(byte[] bytes, long userId) {
        var user = getUserByIdOrThrow(userId);
        File file = new File();
        file.setContent(bytes);
        file.setFileType(FileType.PICTURE);
        file.setUserId(user);
        user.setPhoto(file);

        return userRepository.save(user);
    }

    public File getPhoto(Long id) {
        return userRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("No file with id: " + id)
        ).getPhoto();
    }

    private User getUserByIdOrThrow(long id) {
        return userRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("No user with id: " + id)
        );
    }
}
