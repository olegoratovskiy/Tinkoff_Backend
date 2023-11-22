package com.example.demo.service;

import com.example.demo.entity.Post;
import com.example.demo.exceptions.CreatingExistingEntityException;
import com.example.demo.repository.PostRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class PostService {
    private PostRepository postRepository;
    private WorkService workService;

    public Post getPost(Long id) {
        return postRepository.findById(id).orElseThrow(RuntimeException::new);
    }

    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    public List<Post> getAllPostsByWorkId(Long id) {
        return postRepository.findAllByWorkId(workService.getWorkById(id));
    }

    public Post createPost(Post post, Long workId) {

        if (this.postExists(post, workId)){
            throw new CreatingExistingEntityException(String.format("Post with Title<%s> and Description <%s> and Work <%s> already exists", post.getTitle(), post.getDescription(), workId));
        }

        post.setWorkId(workService.getWorkById(workId));
        return postRepository.save(post);
    }

    @Transactional
    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }

    public boolean postExists(Post model, Long workId) {
        return postRepository.existsPostByTitleAndDescriptionAndWorkId(model.getTitle(), model.getDescription(), workService.getWorkById(workId));
    }

}
