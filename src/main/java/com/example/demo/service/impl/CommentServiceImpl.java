package com.example.demo.service.impl;

import com.example.demo.entity.Comment;
import com.example.demo.entity.Post;
import com.example.demo.exception.BlogAPIException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.payload.CommentDto;
import com.example.demo.repos.CommentRepository;
import com.example.demo.repos.PostRepository;
import com.example.demo.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {
    private CommentRepository commentRepository;
    private PostRepository postRepository;
    private ModelMapper modelMapper;


    public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository,ModelMapper modelMapper) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.modelMapper=modelMapper;
    }

    @Override
    public CommentDto createComment(CommentDto commentDto, Long postId) {
        Comment comment = mapDtoToEntity(commentDto, postId);
        Comment savedComment = commentRepository.save(comment);
         return mapEntityToDto(savedComment);
    }


    private Comment mapDtoToEntity(CommentDto commentDto, Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("post", "id", postId.toString()));
        return Comment.builder().id(commentDto.getId())
                .body(commentDto.getBody()).email(commentDto.getEmail()).name(commentDto.getName()).post(post).build();
    }
    private CommentDto mapEntityToDto(Comment savedComment) {
        return modelMapper.map(savedComment,CommentDto.class);
    }


    @Override
    public List<CommentDto> getAllCommentsByPost(Long postId) {
        List<Comment> comments = commentRepository.findByPostId(postId);
        return comments.stream().map(this::mapEntityToDto).collect(Collectors.toList());
    }

    @Override
    public CommentDto getCommentById(Long id, Long postId) {
        List<Comment> comments = commentRepository.findByPostId(postId);
        Comment commentDto = comments.stream().
                filter(comment -> comment.getId().equals(id)).
                findFirst().orElseThrow(() ->new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment does not belong to post"));
        return mapEntityToDto(commentDto);
    }

    @Override
    public CommentDto updateComment(CommentDto commentDto, Long id, Long postId) {
        CommentDto commentById = getCommentById(id, postId);
        commentById.setBody(commentDto.getBody());
        commentById.setEmail(commentDto.getEmail());
        commentById.setName(commentDto.getName());
        Comment comment = commentRepository.save(mapDtoToEntity(commentById, postId));
        return mapEntityToDto(comment);
    }

    @Override
    public void deleteCommentById(Long id, Long postId) {
        CommentDto commentById = getCommentById(id, postId);
        commentRepository.delete(mapDtoToEntity(commentById,postId));
    }
}
