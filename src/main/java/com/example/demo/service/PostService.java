package com.example.demo.service;

import com.example.demo.entity.Post;
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

    public Post getPost(Long id){
        return postRepository.findById(id).orElseThrow(RuntimeException::new);
    }

    public List<Post> getAllPosts(){
        return postRepository.findAll();
    }

    public Post createPost(Post post,Long id){
        post.setWorkId(workService.getWorkById(id));
        return postRepository.save(post);
    }
    @Transactional
    public void deletePost(Long id){
        postRepository.deleteById(id);
    }
}
