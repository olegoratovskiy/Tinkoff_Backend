package com.example.demo.controller;

import com.example.demo.dto.request.PostRequestDto;
import com.example.demo.dto.response.PostResponseDto;
import com.example.demo.entity.Post;
import com.example.demo.exceptions.CreatingExistingEntityException;
import com.example.demo.mapper.PostMapper;
import com.example.demo.service.PostService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*")
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
        Post model = postMapper.fromDtoToModel(postRequestDto);
        Long workId = postRequestDto.getIdWork();

        if (postService.postExists(model, workId)) {
            throw new CreatingExistingEntityException(String.format("Post with Title<%s> and Description <%s> and Work <%s> already exists", model.getTitle(), model.getDescription(), workId));
        }

        return postMapper.fromModelToDto(postService.createPost(model,workId));
    }

    @GetMapping("/find_all_by_work_id/{id}")
    public List<PostResponseDto> getPosts(@PathVariable @Valid Long id) {
        List<PostResponseDto> list = new ArrayList<>();
        List<Post> postList = postService.getAllPostsByWorkId(id);
        for (Post post : postList) {
            list.add(postMapper.fromModelToDto(post));
        }
        return list;
    }

    @DeleteMapping("/delete{id}")
    public void deletePost(@PathVariable @Valid Long id){
        postService.deletePost(id);
    }

}
