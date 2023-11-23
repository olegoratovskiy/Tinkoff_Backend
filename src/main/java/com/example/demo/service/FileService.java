package com.example.demo.service;

import com.example.demo.entity.File;
import com.example.demo.entity.Post;
import com.example.demo.enums.FileType;
import com.example.demo.repository.FileRepository;
import com.example.demo.repository.PostRepository;
import org.springframework.stereotype.Service;

@Service
public class FileService {

    private final FileRepository fileRepository;
    private final PostRepository postRepository;

    public FileService(FileRepository fileRepository, PostRepository postRepository) {
        this.fileRepository = fileRepository;
        this.postRepository = postRepository;
    }

    public File saveFile(byte[] bytes, long postId) {
        var post = getPostByIdOrThrow(postId);

        File file = new File();
        file.setContent(bytes);
        file.setFileType(FileType.PICTURE); // TODO: put true value
        file.setPostId(post);

        return fileRepository.save(file);
    }

    public File getFile(long fileId) {
        return fileRepository.findById(fileId).orElseThrow(
                () -> new IllegalArgumentException("No file with id: " + fileId)
        );
    }

    private Post getPostByIdOrThrow(long id) {
        return postRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("No post with id: " + id)
        );
    }
}
