package com.example.demo.service.impl;

import com.example.demo.entity.Post;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.payload.PostDto;
import com.example.demo.repos.PostRepository;
import com.example.demo.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostServiceImpl  implements PostService {

    @Autowired
    PostRepository postRepository;
    @Autowired
    ModelMapper modelMapper;
    @Override
    public PostDto createPost(PostDto postDto) {
        Post post = Post.builder()
                .id(postDto.getId())
                .content(postDto.getContent())
                .title(postDto.getTitle())
                .description(postDto.getDescription())
                .build();

        Post savedPost = postRepository.save(post);

        return mapPostToDto(savedPost);
    }

    private PostDto mapPostToDto(Post savedPost) {
        return modelMapper.map(savedPost,PostDto.class);
    }

    @Override
    public List<PostDto> getAllPosts() {

        List<Post> posts = postRepository.findAll();
        PostDto[] postDtos = posts.stream().map(this::mapPostToDto).toArray(PostDto[]::new);
        return List.of(postDtos);
    }

    @Override
    public PostDto getPostById(Long id) {
         return mapPostToDto(postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("post","id",id.toString())));
    }

    @Override
    public PostDto updatePost(PostDto postDto, Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("post", "id", id.toString()));
        post.setContent(postDto.getContent());
        post.setDescription(postDto.getDescription());
        post.setTitle(postDto.getTitle());
        Post save = postRepository.save(post);
        return mapPostToDto(save);

    }

    @Override
    public void deletePostbyId(Long id) {
        postRepository.deleteById(id);
    }
}
