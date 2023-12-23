package com.BloggingApp.service;

import com.BloggingApp.payload.PostDto;
import com.BloggingApp.utils.PostResponse;

import java.util.List;

public interface PostService {
    PostDto savePost(PostDto postDto);
    PostDto updatePost(Long id,PostDto postDto);
    List<PostDto> findAllPosts();
    PostDto findPostById(Long id);
    void deleteById(Long id);
    PostResponse findAllPageAndSort(int pageNo,int pageSize,String sortBy,String sortDir);
}
