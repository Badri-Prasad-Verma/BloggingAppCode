package com.BloggingApp.service;

import com.BloggingApp.payload.CommentDto;
import java.util.List;
public interface CommentService {
    CommentDto saveComment(CommentDto commentDto,Long postId);
    CommentDto updateComments(Long commentId,Long postId,CommentDto commentDto);
    CommentDto findByCommentId(Long commentId);
    List<CommentDto> findCommentsByPostId(Long postId);
    List<CommentDto> findAllComments();

}
