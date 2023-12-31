package com.example.demo.controller;

import com.example.demo.dto.request.PostRequestDto;
import com.example.demo.dto.response.PageInfoResponse;
import com.example.demo.dto.response.PostResponseDto;
import com.example.demo.dto.response.PostsResponseDto;
import com.example.demo.entity.Post;
import com.example.demo.mapper.PostMapper;
import com.example.demo.service.PostService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/posts")
@AllArgsConstructor
public class PostController {
    private PostMapper postMapper;
    private PostService postService;

    @Transactional
    @GetMapping("/get/all")
    public List<PostResponseDto> getAllPosts() {
        return postService.getAllPosts().stream()
                .map(postMapper::fromModelToDto)
                .toList();
    }

    @Transactional
    @GetMapping("/get/{id}")
    public PostResponseDto getPost(@PathVariable @Valid Long id) {
        return postMapper.fromModelToDto(postService.getPost(id));
    }

    @Transactional
    @PostMapping("/create")
    public PostResponseDto createPost(@RequestBody @Valid PostRequestDto postRequestDto) {
        Post model = postMapper.fromDtoToModel(postRequestDto);
        Long workId = postRequestDto.getIdWork();
        String token = postRequestDto.getToken();
        var post = postService.createPost(model, workId, token);
        return postMapper.fromModelToDto(post);
    }

    @Transactional
    @GetMapping("/get")
    public PostsResponseDto getPosts(
            @RequestParam long workId,
            @RequestParam int pageNumber,
            @RequestParam int pageSize
    ) {
        Page<Post> posts = postService.getPostsByWorkId(workId, pageNumber, pageSize);
        PageInfoResponse pageInfo = new PageInfoResponse(
                posts.getTotalPages(),
                posts.getTotalElements(),
                posts.getNumber(),
                posts.getNumberOfElements()
        );
        return new PostsResponseDto(
                posts.getContent().stream().map(postMapper::fromModelToDto).toList(),
                pageInfo
        );
    }

    @DeleteMapping("/delete/{id}")
    public void deletePost(@PathVariable @Valid Long id) {
        postService.deletePost(id);
    }

}
