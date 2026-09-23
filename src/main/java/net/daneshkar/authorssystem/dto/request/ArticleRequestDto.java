package net.daneshkar.authorssystem.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.daneshkar.authorssystem.entity.ArticleStatus;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleRequestDto {

    @NotBlank(message = "Title is required")
    @Size(max = 255, message = "Title must be at most 255 characters")
    private String title;

    @NotBlank(message = "Content is required")
    private String content;

    @NotNull(message = "Category id is required")
    @Positive(message = "Category id must be positive")
    private Long categoryId;

    private ArticleStatus status;
}
