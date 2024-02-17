package com.example.demo.controller;

import com.example.demo.payload.CommentDto;
import com.example.demo.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/api")
public class CommentController {
    @Autowired
    CommentService commentService;

    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<CommentDto> createComment(@PathVariable(value = "postId") long postId,
                                                    @RequestBody CommentDto commentDto) {
        return new ResponseEntity<>(commentService.createComment(commentDto, postId), HttpStatus.CREATED);
    }

    @GetMapping("/posts/{postId}/comments")
    public List<CommentDto> getAllComments(@PathVariable(value = "postId") Long postId) {
        return commentService.getAllCommentsByPost(postId);
    }

    @GetMapping("/posts/{postId}/comments/{id}")
    public ResponseEntity<CommentDto> getCommentById(@PathVariable(value = "id") Long id, @PathVariable(value = "postId") Long postId) {
        CommentDto commentById = commentService.getCommentById(id, postId);
        return new ResponseEntity<>(commentById, HttpStatus.OK);
    }

    @PutMapping("/posts/{postId}/comments/{id}")
    public ResponseEntity<CommentDto> updateComment(@RequestBody CommentDto commentDto, @PathVariable(value = "postId") Long postId, @PathVariable(value = "id") Long id) {
        CommentDto updateComment = commentService.updateComment(commentDto, id, postId);
        return new ResponseEntity<>(updateComment, HttpStatus.OK);
    }

    @DeleteMapping("/posts/{postId}/comments/{id}")
    public ResponseEntity<String> deleteComment(
            @PathVariable(value = "id") Long id, @PathVariable(value = "postId") Long postId) {
        commentService.deleteCommentById(id, postId);
        return new ResponseEntity<>("Comment deleted successfully", HttpStatus.OK);
    }


}
