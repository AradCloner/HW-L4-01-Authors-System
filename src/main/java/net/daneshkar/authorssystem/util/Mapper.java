package net.daneshkar.authorssystem.util;

import net.daneshkar.authorssystem.dto.request.ArticleRequestDto;
import net.daneshkar.authorssystem.dto.request.AuthorRequestDto;
import net.daneshkar.authorssystem.dto.request.CategoryRequestDto;
import net.daneshkar.authorssystem.dto.response.ArticleResponseDto;
import net.daneshkar.authorssystem.entity.Article;
import net.daneshkar.authorssystem.entity.ArticleStatus;
import net.daneshkar.authorssystem.entity.Author;
import net.daneshkar.authorssystem.entity.Category;

public class Mapper {
    public static Author mapToAuthor(AuthorRequestDto authorRequestDto) {
        return Author.builder()
                .firstName(authorRequestDto.getFirstName())
                .lastName(authorRequestDto.getLastName())
                .username(authorRequestDto.getUsername())
                .password(authorRequestDto.getPassword())
                .build();
    }

    public static Article mapToArticle(ArticleRequestDto articleRequestDto) {
        return Article.builder()
                .title(articleRequestDto.getTitle())
                .content(articleRequestDto.getContent())
                .status(articleRequestDto.getStatus() != null ? articleRequestDto.getStatus() : ArticleStatus.DRAFT)
                .build();
    }

    public static ArticleResponseDto mapToArticleResponse(Article article) {
        return ArticleResponseDto.builder()
                .title(article.getTitle())
                .content(article.getContent())
                .status(article.getStatus())
                .categoryId(article.getCategory().getId())
                .categoryTitle(article.getCategory().getTitle())
                .authorId(article.getAuthor().getId())
                .build();
    }

    public static Category mapToCategory(CategoryRequestDto categoryRequestDto) {
        return Category.builder()
                .title(categoryRequestDto.getTitle())
                .build();
    }
}
