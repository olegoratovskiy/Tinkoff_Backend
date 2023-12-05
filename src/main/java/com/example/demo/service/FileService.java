package com.example.demo.service;

import com.example.demo.entity.File;
import com.example.demo.entity.Post;
import com.example.demo.entity.User;
import com.example.demo.enums.FileType;
import com.example.demo.repository.FileRepository;
import com.example.demo.repository.PostRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class FileService {

    private final FileRepository fileRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public FileService(FileRepository fileRepository, PostRepository postRepository, UserRepository userRepository) {
        this.fileRepository = fileRepository;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    private User getUserByIdOrThrow(long id) {
        return userRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("No user with id: " + id)
        );
    }

    public File saveFile(byte[] bytes, long postId) {
        var post = getPostByIdOrThrow(postId);

        File file = new File();
        file.setContent(bytes);
        file.setFileType(FileType.PICTURE); // TODO: put true value
        file.setPostId(post);

        return fileRepository.save(file);
    }

    public File updateFile(byte[] bytes, long fileId) {
        var file = getFile(fileId);
        file.setContent(bytes);
        return fileRepository.save(file);
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

    public File getFile(long fileId) {
        return fileRepository.findById(fileId).orElseThrow(
                () -> new IllegalArgumentException("No file with id: " + fileId)
        );
    }

    public File getPhoto(Long id) {
        return userRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("No file with id: " + id)
        ).getPhoto();
    }

    private Post getPostByIdOrThrow(long id) {
        return postRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("No post with id: " + id)
        );
    }
}
