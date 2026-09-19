package net.daneshkar.authorssystem.dto;


import exception.InvalidUserNameException;
import exception.WeakPasswordException;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;


@Getter
@Setter
public class Author {
    private static long nextId = 1L;

    private Long id;
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private Set<Article> articles;

    public Author() {
        this.id = nextId++;
        this.articles = new HashSet<>();
    }

    public Author(String firstName, String lastName, String username, String password) throws InvalidUserNameException, WeakPasswordException {

        if (username.length() < 5) {
            throw new InvalidUserNameException("نام کاربری باید حداقل 5 کاراکتر باشد!");
        }

        // Validation password
        if (password.length() < 8) {
            throw new WeakPasswordException("رمز عبور باید حداقل 8 کاراکتر باشد!");
        }

        this.id = nextId++;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.articles = new HashSet<>();
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
