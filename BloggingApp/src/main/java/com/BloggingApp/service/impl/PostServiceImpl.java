package com.BloggingApp.service.impl;

import com.BloggingApp.Repository.PostRepository;
import com.BloggingApp.entity.Post;
import com.BloggingApp.exception.ResourceNotFoundException;
import com.BloggingApp.payload.PostDto;
import com.BloggingApp.service.PostService;
import com.BloggingApp.utils.PostResponse;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {
    public ModelMapper modelMapper;
    private PostRepository postRepository;

    public PostServiceImpl(ModelMapper modelMapper, PostRepository postRepository) {
        this.modelMapper = modelMapper;
        this.postRepository = postRepository;
    }

    @Override
    public PostDto savePost(PostDto postDto) {
        Post post = dtoToEntity(postDto);
        Post savedPost = postRepository.save(post);
        PostDto dto = entityToDto(savedPost);
        return dto;
    }

    @Override
    public PostDto updatePost(Long id, PostDto postDto) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());
        Post updatedPost = postRepository.save(post);
        PostDto dto = entityToDto(updatedPost);
        return dto;
    }

    @Override
    public List<PostDto> findAllPosts() {
        List<Post> all = postRepository.findAll();
        return all.stream().map(post -> entityToDto(post)).collect(Collectors.toList());
    }

    @Override
    public PostDto findPostById(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
        return entityToDto(post);
    }

    @Override
    public void deleteById(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
        postRepository.delete(post);
    }

    @Override
    public PostResponse findAllPageAndSort(int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        PageRequest pageRequest = PageRequest.of(pageNo, pageSize, sort);
        Page<Post> pages = postRepository.findAll(pageRequest);
        List<Post> content = pages.getContent();
        List<PostDto> collect = content.stream().map(post -> entityToDto(post)).collect(Collectors.toList());
        PostResponse postResponse=new PostResponse();
        postResponse.setPostDto(collect);
        postResponse.setPageNo(pages.getNumber());
        postResponse.setPageSize(pages.getSize());
        postResponse.setTotalElements(pages.getNumberOfElements());
        postResponse.setTotalPages(pages.getTotalPages());
        postResponse.setLast(pages.isLast());
        return postResponse;
    }


    // Entity to Dto
    private PostDto entityToDto(Post post){
        return modelMapper.map(post,PostDto.class);
    }

    //Dto to entity
    private Post dtoToEntity(PostDto postDto){
        return modelMapper.map(postDto,Post.class);
    }
}
