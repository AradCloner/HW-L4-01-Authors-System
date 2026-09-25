package net.daneshkar.authorssystem.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.daneshkar.authorssystem.entity.ArticleStatus;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArticleResponseDto {

    private String title;
    private String content;
    private ArticleStatus status;
    private Long categoryId;
    private String categoryTitle;
    private Long authorId;
}
