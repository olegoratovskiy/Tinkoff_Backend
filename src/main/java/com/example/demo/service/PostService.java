package com.example.demo.service;

import com.example.demo.entity.Post;
import com.example.demo.entity.Work;
import com.example.demo.exceptions.CreatingExistingEntityException;
import com.example.demo.repository.PostRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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

    public Page<Post> getPostsByWorkId(long workId, int pageNumber, int pageSize) {
        return postRepository.findAllByWorkIdId(
                workId,
                PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Order.asc("id")))
        );
    }

    public Post createPost(Post post, Long workId) {

        if (postExists(post, workId)) {
            String postTitle = post.getTitle();
            String postDescription = post.getDescription();

            String message = String.format("Post with Title<%s> and Description <%s> and Work <%s> already exists",
                    postTitle, postDescription, workId);

            throw new CreatingExistingEntityException(message);
        }

        post.setWorkId(workService.getWorkById(workId));
        return postRepository.save(post);
    }

    @Transactional
    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }

    private boolean postExists(Post post, Long workId) {
        String postTitle = post.getTitle();
        String postDescription = post.getDescription();
        Work work = workService.getWorkById(workId);

        return postRepository.existsPostByTitleAndDescriptionAndWorkId(postTitle, postDescription, work);
    }

}
