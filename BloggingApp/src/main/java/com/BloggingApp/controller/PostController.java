package com.BloggingApp.controller;

import com.BloggingApp.payload.PostDto;
import com.BloggingApp.service.PostService;
import com.BloggingApp.utils.PostResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    private PostService postService;
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/save")
    public ResponseEntity<?> createNewPost(@Valid @RequestBody PostDto postDto, BindingResult result){
        if(result.hasErrors()){
            return new ResponseEntity<>(result.getFieldError().getDefaultMessage(),HttpStatus.BAD_REQUEST);
        }
        PostDto dto = postService.savePost(postDto);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/update/{id}")
    public ResponseEntity<PostDto> updatedPost(@Valid @PathVariable Long id,@RequestBody PostDto postDto){
        PostDto dto = postService.updatePost(id,postDto);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }
    @GetMapping("/findAll")
    public ResponseEntity<PostResponse> findAllPageAndSort(
           @RequestParam(value = "pageNo",defaultValue = "0",required = false) int pageNo,
           @RequestParam(value = "pageSize",defaultValue = "10",required = false) int pageSize,
           @RequestParam(value = "sortBy",defaultValue = "id",required = false) String sortBy,
           @RequestParam(value = "sortDir",defaultValue = "asc",required = false) String sortDir){
        PostResponse allPageAndSort = postService.findAllPageAndSort(pageNo, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(allPageAndSort, HttpStatus.OK);
    }
    @GetMapping("/all")
    public ResponseEntity<List<PostDto>> findAllPosts(){
        List<PostDto> allPosts = postService.findAllPosts();
        return new ResponseEntity<>(allPosts, HttpStatus.OK);
    }
    @GetMapping("/findBy/{id}")
    public ResponseEntity<PostDto> findById(@PathVariable Long id){
        PostDto dto = postService.findPostById(id);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deletePost(@PathVariable Long id){
        postService.deleteById(id);
        return new ResponseEntity<>("Post has been deleted successfully", HttpStatus.OK);
    }

}
