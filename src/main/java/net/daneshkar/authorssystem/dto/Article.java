package net.daneshkar.authorssystem.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
public class Article {
    private static long nextId = 1L;

    private Long id;
    private String title;
    private String content;
    private Category category;
    private ArticleStatus status;

    public Article() {
        this.id = nextId++;
        this.status = ArticleStatus.DRAFT;
    }
    public Article(String title, String content, Category category, ArticleStatus status) {
        this.id = nextId++;
        this.title = title;
        this.content = content;
        this.category = category;
        this.status = status != null ? status : ArticleStatus.DRAFT;
    }


    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Article)) return false;
        return this.id.equals(((Article)obj).id);
    }

    @Override
    public String toString() {
        return "Article{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", status=" + status +
                ", category=" + category +
                '}';
    }
}
