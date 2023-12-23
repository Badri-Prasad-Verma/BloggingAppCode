package com.BloggingApp.service.impl;

import com.BloggingApp.Repository.CommentRepository;
import com.BloggingApp.Repository.PostRepository;
import com.BloggingApp.entity.Comment;
import com.BloggingApp.entity.Post;
import com.BloggingApp.exception.ResourceNotFoundException;
import com.BloggingApp.payload.CommentDto;
import com.BloggingApp.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Service
public class CommentServiceImpl implements CommentService {
    private ModelMapper modelMapper;
    private PostRepository postRepository;
    private CommentRepository commentRepository;

    public CommentServiceImpl(ModelMapper modelMapper, PostRepository postRepository, CommentRepository commentRepository) {
        this.modelMapper = modelMapper;
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
    }

    @Override
    public CommentDto saveComment(CommentDto commentDto, Long postId) {
        Comment comment = dtoToEntity(commentDto);
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "PostId", postId));
        comment.setPost(post);
        Comment savedComment = commentRepository.save(comment);
        CommentDto dto = entityToDto(savedComment);
        return dto;
    }

    @Override
    public CommentDto updateComments(Long commentId, Long postId, CommentDto commentDto) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "PostId", postId));
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment", "commentId", commentId));
        comment.setName(commentDto.getName());
        comment.setEmail(commentDto.getEmail());
        comment.setBody(commentDto.getBody());
        comment.setPost(post);
        Comment updatedComment = commentRepository.save(comment);
        CommentDto dto = entityToDto(updatedComment);
        return dto;
    }

    @Override
    public CommentDto findByCommentId(Long commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment", "commentId", commentId));
        return entityToDto(comment);
    }

    @Override
    public List<CommentDto> findCommentsByPostId(Long postId) {
        List<Comment> commentsByPostId = commentRepository.findCommentsByPostId(postId);
        return commentsByPostId.stream().map(comment -> entityToDto(comment)).collect(Collectors.toList());
    }

    @Override
    public List<CommentDto> findAllComments() {
        List<Comment> all = commentRepository.findAll();
        return all.stream().map(comment -> entityToDto(comment)).collect(Collectors.toList());
    }

    //entity to dto
    private CommentDto entityToDto(Comment comment){
        return modelMapper.map(comment,CommentDto.class);
    }

    //dto to entity
    private Comment dtoToEntity(CommentDto commentDto){
        return modelMapper.map(commentDto,Comment.class);
    }

}
