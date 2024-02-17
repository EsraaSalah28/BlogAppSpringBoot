package com.example.demo.service;

import com.example.demo.payload.PostDto;

import java.util.List;

public interface PostService {
    PostDto createPost(PostDto postDto);
    List<PostDto> getAllPosts();
    PostDto getPostById(Long id);
    PostDto updatePost(PostDto postDto,Long id);
    void deletePostbyId(Long id);
}
