package net.daneshkar.authorssystem.service;

import lombok.RequiredArgsConstructor;
import net.daneshkar.authorssystem.entity.ArticleStatus;
import net.daneshkar.authorssystem.entity.Category;
import net.daneshkar.authorssystem.exception.*;
import net.daneshkar.authorssystem.entity.Author;
import net.daneshkar.authorssystem.entity.Article;
import net.daneshkar.authorssystem.repository.ArticleRepository;
import net.daneshkar.authorssystem.repository.AuthorRepository;
import net.daneshkar.authorssystem.repository.CategoryRepository;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthorService {

    private final AuthorRepository authorRepository;
    private final CategoryRepository categoryRepository;
    private final ArticleRepository articleRepository;


    public void registerAuthor(Author author)
            throws InvalidUserNameException, WeakPasswordException {

        if (authorRepository.findByUsername(author.getUsername()).isPresent()) {
            throw new InvalidUserNameException("نام کاربری تکراری است!");
        }

        if (author.getUsername().length() < 5) {
            throw new InvalidUserNameException("نام کاربری باید حداقل 5 کاراکتر باشد!");
        }

        // Validation password
        if (author.getPassword().length() < 8) {
            throw new WeakPasswordException("رمز عبور باید حداقل 8 کاراکتر باشد!");
        }

        authorRepository.save(author);
    }

    public void addArticle(Long authorId, Long categoryId, Article article)
            throws AuthorNotFoundException, DuplicateArticleException, CategoryNotFoundException {

        if (article == null) {
            throw new IllegalArgumentException("مقاله نمی‌تواند خالی باشد!");
        }

        Author author = authorRepository.findById(authorId)
                .orElseThrow(() -> new AuthorNotFoundException("نویسنده پیدا نشد!"));

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException("دسته‌بندی پیدا نشد!"));

        if (articleRepository.existsByAuthorIdAndTitle(authorId, article.getTitle())) {
            throw new DuplicateArticleException("این عنوان مقاله قبلاً ثبت شده است!");
        }

        article.setAuthor(author);
        article.setCategory(category);

        articleRepository.save(article);
    }



    public void deleteArticle(Long authorId, Long articleId)
            throws ArticleNotFoundException, AuthorNotFoundException {

        authorRepository.findById(authorId)
                .orElseThrow(() -> new AuthorNotFoundException("نویسنده پیدا نشد!"));

        Article article = articleRepository.findByIdAndAuthorId(articleId, authorId)
                .orElseThrow(() -> new ArticleNotFoundException("مقاله پیدا نشد!"));

        articleRepository.delete(article);
    }


    public void changeArticleStatus(Long authorId, Long articleId, ArticleStatus newStatus)
            throws AuthorNotFoundException, ArticleNotFoundException {

        authorRepository.findById(authorId)
                .orElseThrow(() -> new AuthorNotFoundException("نویسنده پیدا نشد!"));

        Article article = articleRepository.findByIdAndAuthorId(articleId, authorId)
                .orElseThrow(() -> new ArticleNotFoundException("مقاله پیدا نشد!"));

        article.setStatus(newStatus);
        articleRepository.save(article);
    }


    public Article searchArticle(Long authorId, Long articleId)
            throws AuthorNotFoundException, ArticleNotFoundException {

        authorRepository.findById(authorId)
                .orElseThrow(() -> new AuthorNotFoundException("نویسنده پیدا نشد!"));

        return articleRepository.findByIdAndAuthorId(articleId, authorId)
                .orElseThrow(() -> new ArticleNotFoundException("مقاله پیدا نشد!"));
    }

    public List<Article> showPublishedArticles(Long authorId)
            throws AuthorNotFoundException {

        authorRepository.findById(authorId)
                .orElseThrow(() -> new AuthorNotFoundException("نویسنده پیدا نشد!"));

        return articleRepository.findByAuthorIdAndStatus(
                authorId,
                ArticleStatus.PUBLISHED
        );
    }

    public Category addCategory(String title) {
        if (categoryRepository.findByTitle(title).isPresent()) {
            throw new IllegalArgumentException("دسته‌بندی تکراری است!");
        }

        Category category = new Category();
        category.setTitle(title);

        return categoryRepository.save(category);
    }


    public List<Category> showCategories() {
        return categoryRepository.findAll();
    }

}
