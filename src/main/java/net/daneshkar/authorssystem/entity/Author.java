package net.daneshkar.authorssystem.entity;


import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "authors")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    @OneToMany(mappedBy = "author")
    private List<Article> articles = new ArrayList<>();



    public Author(String firstName, String lastName, String username, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    @Override
    public String toString() {
        return "Author{" +
                "id=" + id +
                ", name='" + firstName + " " + lastName + '\'' +
                ", username='" + username + '\'' +
                ", articlesCount=" + (articles != null ? articles.size() : 0) +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
         if(!(obj instanceof Author))
             return false;
         return this.id.equals(((Author)obj).id);
    }
}
