package com.example.demo.controller;

import com.example.demo.dto.request.PostRequestDto;
import com.example.demo.dto.response.PostResponseDto;
import com.example.demo.entity.Post;
import com.example.demo.mapper.PostMapper;
import com.example.demo.service.PostService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/posts")
@AllArgsConstructor
public class PostController {
    private PostMapper postMapper;
    private PostService postService;

    @GetMapping("/find/{id}")
    public PostResponseDto getPost(@PathVariable @Valid Long id){
        return postMapper.fromModelToDto(postService.getPost(id));
    }

    @GetMapping("/find_all")
    public List<PostResponseDto> getAllYears() {
        List<PostResponseDto> list = new ArrayList<>();
        List<Post> postList = postService.getAllPosts();
        for (Post post : postList) {
            list.add(postMapper.fromModelToDto(post));
        }
        return list;
    }

    @PostMapping("/create")
    public PostResponseDto createPost(@RequestBody @Valid PostRequestDto postRequestDto){
        return postMapper.fromModelToDto(postService.createPost
                (postMapper.fromDtoToModel(postRequestDto),postRequestDto.getIdWork()));
    }

    @DeleteMapping("/delete{id}")
    public void deletePost(@PathVariable @Valid Long id){
        postService.deletePost(id);
    }

}
