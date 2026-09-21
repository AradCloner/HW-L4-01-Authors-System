package net.daneshkar.authorssystem.repository;

import net.daneshkar.authorssystem.entity.Article;
import net.daneshkar.authorssystem.entity.ArticleStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ArticleRepository extends JpaRepository<Article,Long> {
    boolean existsByAuthorIdAndTitle(Long authorId, String title);
    Optional<Article> findByIdAndAuthorId(Long articleId, Long authorId);
    List<Article> findByAuthorIdAndStatus(Long authorId, ArticleStatus status);
}
