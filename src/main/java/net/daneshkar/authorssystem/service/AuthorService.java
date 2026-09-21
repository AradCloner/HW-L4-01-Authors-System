package net.daneshkar.authorssystem.service;

import lombok.RequiredArgsConstructor;
import net.daneshkar.authorssystem.entity.ArticleStatus;
import net.daneshkar.authorssystem.exception.ArticleNotFoundException;
import net.daneshkar.authorssystem.exception.AuthorNotFoundException;
import net.daneshkar.authorssystem.exception.DuplicateArticleException;
import net.daneshkar.authorssystem.exception.InvalidUserNameException;
import net.daneshkar.authorssystem.exception.WeakPasswordException;
import net.daneshkar.authorssystem.entity.Author;
import net.daneshkar.authorssystem.entity.Article;
import net.daneshkar.authorssystem.repository.ArticleRepository;
import net.daneshkar.authorssystem.repository.AuthorRepository;
import net.daneshkar.authorssystem.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthorService {

    private final AuthorRepository authorRepository;
    private final CategoryRepository categoryRepository;
    private final ArticleRepository articleRepository;



    public void registerAuthor(String firstName, String lastName, String username, String password)
            throws InvalidUserNameException, WeakPasswordException {
        if (username.length() < 5) {
            throw new InvalidUserNameException("نام کاربری باید حداقل 5 کاراکتر باشد!");
        }

        // Validation password
        if (password.length() < 8) {
            throw new WeakPasswordException("رمز عبور باید حداقل 8 کاراکتر باشد!");
        }

        Author author = new Author(firstName, lastName, username, password);
        authorRepository.save(author);
    }

    public Article addArticle(Long authorId, Article article) throws ArticleNotFoundException, AuthorNotFoundException, DuplicateArticleException {
        if (article == null) {
            throw new IllegalArgumentException("مقاله نمی‌تواند خالی باشد!");
        }
        Author author = authorRepository.findById(authorId).orElseThrow(() -> new AuthorNotFoundException("نویسنده پیدا نشد!"));

        boolean isArticleTitleDuplicate = author.getArticles().stream().anyMatch(a -> a.getTitle().equals(article.getTitle()));
        if (articleRepository.existsByAuthorIdAndTitle(authorId,article.getTitle())) {
            throw new DuplicateArticleException("این عنوان مقاله قبلاً ثبت شده است!");
        }
        author.getArticles().add(article);
        return article;
    }

    public void deleteArticle(Long authorId, Long articleId) throws ArticleNotFoundException, AuthorNotFoundException {
        Author author = authors.get(authorId);
        if (author == null) {
            throw new AuthorNotFoundException("نویسنده پیدا نشد!");
        }
        Article article = author.getArticles().stream().filter(a -> a.getId().equals(articleId)).findFirst().orElse(null);
        if (article == null) {
            throw new ArticleNotFoundException("مقاله پیدا نشد!");
        }
        author.getArticles().remove(article);
    }

    public Article changeArticleStatus(Long authorId, Long articleId, ArticleStatus newStatus)
            throws AuthorNotFoundException, ArticleNotFoundException {
        Author author = authors.get(authorId);
        if (author == null) {
            throw new AuthorNotFoundException("نویسنده پیدا نشد!");
        }
        Article article = author.getArticles().stream().filter(a -> a.getId().equals(articleId)).findFirst().orElse(null);
        if (article == null) {
            throw new ArticleNotFoundException("مقاله پیدا نشد!");
        }
        article.setStatus(newStatus);
        return article;
    }
    public Article searchArticle(Long authorId, Long articleId)
            throws AuthorNotFoundException, ArticleNotFoundException {
        Author author = authors.get(authorId);
        if (author == null) {
            throw new AuthorNotFoundException("نویسنده پیدا نشد!");
        }
        Article article = author.getArticles().stream().filter(a -> a.getId().equals(articleId)).findFirst().orElse(null);
        if (article == null) {
            throw new ArticleNotFoundException("مقاله پیدا نشد!");
        }
        return article;
    }
    public Set<Article> showPublishedArticles(Long authorId)
            throws AuthorNotFoundException {
        Author author = authors.get(authorId);
        if (author == null) {
            throw new AuthorNotFoundException("نویسنده پیدا نشد!");
        }
        return author.getArticles().stream().filter(a -> a.getStatus() == ArticleStatus.PUBLISHED).collect(Collectors.toSet());
    }
}
