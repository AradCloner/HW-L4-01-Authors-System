package net.daneshkar.authorssystem.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.daneshkar.authorssystem.entity.ArticleStatus;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChangeArticleStatusRequestDto {

    @NotNull(message = "Article status is required")
    private ArticleStatus status;

    @NotNull(message = "Article id is required")
    private Long articleId;

    @NotNull(message = "Author id is required")
    private Long authorId;
}
