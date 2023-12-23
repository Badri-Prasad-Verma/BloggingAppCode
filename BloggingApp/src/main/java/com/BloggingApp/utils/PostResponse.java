package com.BloggingApp.utils;

import com.BloggingApp.payload.PostDto;
import lombok.Data;
import java.util.List;
@Data
public class PostResponse {
    List<PostDto> postDto;
    private int pageNo;
    private int pageSize;
    private int totalElements;
    private int totalPages;
    private boolean isLast;

}
