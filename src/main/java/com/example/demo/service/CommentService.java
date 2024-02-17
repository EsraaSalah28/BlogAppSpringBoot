package com.example.demo.service;

import com.example.demo.payload.CommentDto;

import java.util.List;

public interface CommentService {
    CommentDto createComment(CommentDto commentDto,Long postId);
    List<CommentDto> getAllCommentsByPost(Long postId);
    CommentDto getCommentById(Long id,Long postId);
    CommentDto updateComment(CommentDto commentDto,Long id,Long postId);
    void deleteCommentById(Long id,Long postId);
}
