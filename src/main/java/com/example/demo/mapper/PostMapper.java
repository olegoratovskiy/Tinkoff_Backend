package com.example.demo.mapper;

import com.example.demo.dto.request.PostRequestDto;
import com.example.demo.dto.response.PostResponseDto;
import com.example.demo.entity.Comment;
import com.example.demo.entity.File;
import com.example.demo.entity.Post;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
public interface PostMapper {
    @Mapping(target = "commentsId", source = "post.comments", qualifiedByName = "setCommentId")
    @Mapping(target = "filesId", source = "post.files", qualifiedByName = "setFileId")
    PostResponseDto fromModelToDto(Post post);

    @Mapping(target = "comments", source = "postRequestDto.commentsId", qualifiedByName = "setComments")
    @Mapping(target = "files", source = "postRequestDto.filesId", qualifiedByName = "setFiles")
    Post fromDtoToModel(PostRequestDto postRequestDto);


    @Named("setComments")
    static List<Comment> setComments(List<Long> commentsId) {
        if (commentsId != null) {
            return commentsId.stream()
                    .map(id -> {
                        Comment comment = new Comment();
                        comment.setId(id);
                        return comment;
                    }).toList();
        }
        return new ArrayList<>();
    }

    @Named("setCommentId")
    static List<Long> setCommentId(List<Comment> comments) {
        if (comments != null) {
            return comments.stream()
                    .map(Comment::getId)
                    .toList();
        }
        return new ArrayList<>();
    }

    @Named("setFiles")
    static List<File> setFiles(List<Long> filesId) {
        if (filesId != null) {
            return filesId.stream()
                    .map(id -> {
                        File file = new File();
                        file.setId(id);
                        return file;
                    }).toList();
        }
        return new ArrayList<>();
    }

    @Named("setFileId")
    static List<Long> setFileId(List<File> files) {
        if (files != null) {
            return files.stream()
                    .map(File::getId)
                    .toList();
        }
        return new ArrayList<>();
    }
}
