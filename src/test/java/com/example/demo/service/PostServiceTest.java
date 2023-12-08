package com.example.demo.service;


import static org.mockito.Mockito.when;

import com.example.demo.entity.Post;
import com.example.demo.entity.User;

import com.example.demo.repository.PostRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.WorkRepository;
import com.example.demo.utils.JwtTokenUtils;

import org.junit.jupiter.api.Test;

import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.*;

@SpringBootTest
public class PostServiceTest {


    @Autowired
    private PostService postService;

    @MockBean
    private PostRepository postRepository;

    @Autowired
    private WorkService workService;

    @MockBean
    private UserService userService;

    @Autowired
    private JwtTokenUtils jwtTokenUtils;

    @MockBean
    private WorkRepository workRepository;

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private CommentService commentService;


    @Test
    public void testGetPostNotFound() {
        Long postId = 1L;
        when(postRepository.findById(postId)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> postService.getPost(postId));

        verify(postRepository).findById(postId);
    }

    @Test
    public void testGetPostExists() {
        Post post1 = new Post();
        post1.setTitle("title1");
        post1.setDescription("descriptionPost1");
        Post post2 = new Post();
        post2.setTitle("title2");
        post2.setDescription("descriptionPost2");

        List<Post> expectedPosts = new ArrayList<>();
        expectedPosts.add(post1);
        expectedPosts.add(post2);

        when(postRepository.findAll()).thenReturn(expectedPosts);

        List<Post> actualPosts = postService.getAllPosts();

        assertEquals(expectedPosts, actualPosts);
        verify(postRepository).findAll();
    }

    @Test
    void testCreatePost() {
        Post mockPost = new Post();
        Long mockWorkId = 1L;
        String mockToken = "mockToken";

        WorkService workServiceMock = mock(WorkService.class);
        when(workServiceMock.getWorkById(Mockito.eq(mockWorkId))).thenReturn(null);

        User mockUser = new User();
        Mockito.when(userService.findByUserName(eq("mockUsername"))).thenReturn(Optional.of(mockUser));

        JwtTokenUtils jwtTokenUtils = mock(JwtTokenUtils.class);
        Mockito.when(jwtTokenUtils.getUsername(eq(mockToken))).thenReturn("mockUsername");


        Mockito.when(postRepository.save(any())).thenReturn(mockPost);

        PostService postService = new PostService(postRepository, workServiceMock, userService, jwtTokenUtils);


        Post createdPost = postService.createPost(mockPost, mockWorkId, mockToken);


        assertNotNull(createdPost);

        System.out.println(createdPost);

        Mockito.verify(postRepository, Mockito.times(1)).save(any());
    }


}
