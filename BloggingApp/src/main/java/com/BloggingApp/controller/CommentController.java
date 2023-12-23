package com.BloggingApp.controller;

import com.BloggingApp.payload.CommentDto;
import com.BloggingApp.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController
@RequestMapping("/api/comments")
public class CommentController {

    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/post/{postId}")
    public ResponseEntity<CommentDto> saveComment(@RequestBody CommentDto commentDto, @PathVariable Long postId) {
        CommentDto savedComment = commentService.saveComment(commentDto, postId);
        return new ResponseEntity<>(savedComment, HttpStatus.CREATED);
    }

    @PutMapping("/{commentId}/post/{postId}")
    public ResponseEntity<CommentDto> updateComments(@PathVariable Long commentId,
                                                     @PathVariable Long postId,
                                                     @RequestBody CommentDto commentDto) {
        CommentDto updatedComment = commentService.updateComments(commentId, postId, commentDto);
        return new ResponseEntity<>(updatedComment, HttpStatus.OK);
    }

    @GetMapping("/{commentId}")
    public ResponseEntity<CommentDto> findByCommentId(@PathVariable Long commentId) {
        CommentDto comment = commentService.findByCommentId(commentId);
        return new ResponseEntity<>(comment, HttpStatus.OK);
    }

    @GetMapping("/post/{postId}")
    public ResponseEntity<List<CommentDto>> findCommentsByPostId(@PathVariable Long postId) {
        List<CommentDto> comments = commentService.findCommentsByPostId(postId);
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<CommentDto>> findAllComments() {
        List<CommentDto> comments = commentService.findAllComments();
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }
}
