package com.BloggingApp.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostDto {
    private Long id;

    @NotNull
    @Size(min = 3,message = "Tile should at least 3 characters")
    private String title;

    @NotBlank
    @Size(min = 3,message = "Description should at least 3 characters")
    private String description;

    @NotEmpty
    @Size(min = 3,message = "Content should at least 3 characters")
    private String content;
}
