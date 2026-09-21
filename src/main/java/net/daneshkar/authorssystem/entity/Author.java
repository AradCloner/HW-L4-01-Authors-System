package net.daneshkar.authorssystem.entity;


import jakarta.persistence.*;
import lombok.*;
import net.daneshkar.authorssystem.exception.InvalidUserNameException;
import net.daneshkar.authorssystem.exception.WeakPasswordException;

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



    public Author(String firstName, String lastName, String username, String password) throws InvalidUserNameException, WeakPasswordException {

        if (username.length() < 5) {
            throw new InvalidUserNameException("نام کاربری باید حداقل 5 کاراکتر باشد!");
        }

        // Validation password
        if (password.length() < 8) {
            throw new WeakPasswordException("رمز عبور باید حداقل 8 کاراکتر باشد!");
        }


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
