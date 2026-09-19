package net.daneshkar.authorssystem.service;

import net.daneshkar.authorssystem.dto.ArticleStatus;
import net.daneshkar.authorssystem.exception.ArticleNotFoundException;
import net.daneshkar.authorssystem.exception.AuthorNotFoundException;
import net.daneshkar.authorssystem.exception.DuplicateArticleException;
import net.daneshkar.authorssystem.exception.InvalidUserNameException;
import net.daneshkar.authorssystem.exception.WeakPasswordException;
import net.daneshkar.authorssystem.dto.Author;
import net.daneshkar.authorssystem.dto.Article;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class AuthorService {

    private static AuthorService instance;

    private Map<Long, Author> authors;

    private AuthorService() {
        this.authors = new HashMap<>();
    }

    public static AuthorService getInstance() {
        if(instance == null) {
            instance = new AuthorService();
        }
        return instance;
    }

    public Author registerAuthor(String firstName, String lastName, String username, String password)
            throws InvalidUserNameException, WeakPasswordException {
        Author author = new Author(firstName, lastName, username, password);
        authors.put(author.getId(), author);
        return author;
    }

    public Article addArticle(Long authorId, Article article) throws ArticleNotFoundException, AuthorNotFoundException, DuplicateArticleException {
        if (article == null) {
            throw new IllegalArgumentException("مقاله نمی‌تواند خالی باشد!");
        }
        Author author = authors.get(authorId);
        if (author == null) {
            throw new AuthorNotFoundException("نویسنده پیدا نشد!");
        }
        boolean isArticleTitleDuplicate = author.getArticles().stream().anyMatch(a -> a.getTitle().equals(article.getTitle()));
        if (isArticleTitleDuplicate) {
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
