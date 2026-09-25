package net.daneshkar.authorssystem.cotroller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import net.daneshkar.authorssystem.dto.BaseResponse;
import net.daneshkar.authorssystem.dto.request.ArticleRequestDto;
import net.daneshkar.authorssystem.dto.request.AuthorRequestDto;
import net.daneshkar.authorssystem.dto.request.CategoryRequestDto;
import net.daneshkar.authorssystem.dto.request.ChangeArticleStatusRequestDto;
import net.daneshkar.authorssystem.dto.response.ArticleResponseDto;
import net.daneshkar.authorssystem.entity.Category;
import net.daneshkar.authorssystem.exception.*;
import net.daneshkar.authorssystem.service.AuthorService;
import net.daneshkar.authorssystem.util.Mapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ManagementSystemController {

    private final AuthorService authorService;

    @PostMapping("/authors")
    @ResponseStatus(HttpStatus.CREATED)
    public BaseResponse<Void> registerAuthor(@Valid @RequestBody AuthorRequestDto authorRequestDto)
            throws InvalidUserNameException, WeakPasswordException {
        authorService.registerAuthor(Mapper.mapToAuthor(authorRequestDto));
        return BaseResponse.success(null, HttpStatus.CREATED.value(), "Author registered successfully");
    }

    @PostMapping("/authors/{authorId}/articles")
    @ResponseStatus(HttpStatus.CREATED)
    public BaseResponse<Void> addArticle(@PathVariable Long authorId,
                                         @Valid @RequestBody ArticleRequestDto articleRequestDto)
            throws AuthorNotFoundException, DuplicateArticleException, CategoryNotFoundException {
        authorService.addArticle(authorId, articleRequestDto.getCategoryId(), Mapper.mapToArticle(articleRequestDto));
        return BaseResponse.success(null, HttpStatus.CREATED.value(), "Article added successfully");
    }

    @DeleteMapping("/authors/{authorId}/articles/{articleId}")
    public BaseResponse<Void> deleteArticle(@PathVariable Long authorId, @PathVariable Long articleId)
            throws ArticleNotFoundException, AuthorNotFoundException {
        authorService.deleteArticle(authorId, articleId);
        return BaseResponse.success(null, HttpStatus.OK.value(), "Article deleted successfully");
    }

    @PatchMapping("/articles/status")
    public BaseResponse<Void> changeArticleStatus(@Valid @RequestBody ChangeArticleStatusRequestDto requestDto)
            throws AuthorNotFoundException, ArticleNotFoundException {
        authorService.changeArticleStatus(requestDto.getAuthorId(), requestDto.getArticleId(), requestDto.getStatus());
        return BaseResponse.success(null, HttpStatus.OK.value(), "Article status changed successfully");
    }

    @GetMapping("/authors/{authorId}/articles/{articleId}")
    public BaseResponse<ArticleResponseDto> searchArticle(@PathVariable Long authorId, @PathVariable Long articleId)
            throws AuthorNotFoundException, ArticleNotFoundException {
        ArticleResponseDto article = Mapper.mapToArticleResponse(authorService.searchArticle(authorId, articleId));
        return BaseResponse.success(article, HttpStatus.OK.value(), "Article found");
    }

    @GetMapping("/authors/{authorId}/articles/published")
    public BaseResponse<List<ArticleResponseDto>> showPublishedArticles(@PathVariable Long authorId)
            throws AuthorNotFoundException {
        List<ArticleResponseDto> articles = authorService.showPublishedArticles(authorId).stream()
                .map(Mapper::mapToArticleResponse)
                .toList();
        return BaseResponse.success(articles, HttpStatus.OK.value(), "Published articles");
    }

    @PostMapping("/categories")
    @ResponseStatus(HttpStatus.CREATED)
    public BaseResponse<Category> addCategory(@Valid @RequestBody CategoryRequestDto categoryRequestDto) {
        Category category = authorService.addCategory(categoryRequestDto.getTitle());
        return BaseResponse.success(category, HttpStatus.CREATED.value(), "Category added successfully");
    }

    @GetMapping("/categories")
    public BaseResponse<List<Category>> showCategories() {
        return BaseResponse.success(authorService.showCategories(), HttpStatus.OK.value(), "Categories");
    }
}
