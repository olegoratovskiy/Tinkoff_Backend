package com.example.demo.service;

import com.example.demo.entity.Comment;
import com.example.demo.entity.Post;
import com.example.demo.entity.User;
import com.example.demo.entity.Work;
import com.example.demo.model.CreateCommentModel;
import com.example.demo.repository.CommentRepository;
import com.example.demo.repository.NewsRepository;
import com.example.demo.repository.PostRepository;

import com.example.demo.utils.JwtTokenUtils;
import jakarta.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;


@SpringBootTest
@Transactional
class CommentServiceTest {

    @Autowired
    private CommentService commentService;

    @Autowired
    private PostService postService;

    @Autowired
    private WorkService workService;

    @MockBean
    private UserService userService;

    @MockBean
    private PostRepository postRepository;

    @MockBean
    private CommentRepository commentRepository;
    @MockBean

    private NewsRepository newsRepository;


    @Test
    void testCreateComment() {
        CreateCommentModel mockModel = new CreateCommentModel(
                "mockContent",
                1L,
                LocalDateTime.now(),
                true
        );
        String mockToken = "mockToken";

        User mockUser = new User();
        mockUser.setName("mockUserName");

        Comment mockComment = new Comment();
        mockComment.setId(1L);

        Post mockPost = new Post();
        mockPost.setId(1L);


        CommentRepository commentRepositoryMock = mock(CommentRepository.class);
        when(commentRepositoryMock.save(any(Comment.class))).thenReturn(mockComment);

        UserService userServiceMock = mock(UserService.class);
        when(userServiceMock.findByUserName(eq("mockUserName"))).thenReturn(Optional.of(mockUser));

        JwtTokenUtils jwtTokenUtilsMock = mock(JwtTokenUtils.class);
        when(jwtTokenUtilsMock.getUsername(eq(mockToken))).thenReturn("mockUserName");

        WorkService workServiceMock = mock(WorkService.class);
        when(workServiceMock.getWorkById(eq(1L))).thenReturn(new Work());

        PostRepository postRepositoryMock = mock(PostRepository.class);
        when(postRepositoryMock.findById(eq(1L))).thenReturn(Optional.of(mockPost));


        CommentService commentService = new CommentService(
                commentRepositoryMock,
                postRepositoryMock,
                userServiceMock,
                jwtTokenUtilsMock,
                newsRepository);

        Comment createdComment = commentService.createComment(mockModel, mockToken);

        assertNotNull(createdComment);
        assertEquals(mockComment.getId(), createdComment.getId());


        verify(commentRepositoryMock, times(1)).save(any(Comment.class));

        verify(userServiceMock, times(1)).findByUserName(eq("mockUserName"));
        verify(jwtTokenUtilsMock, times(1)).getUsername(eq(mockToken));

    }
}

