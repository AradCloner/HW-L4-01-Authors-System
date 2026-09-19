package net.daneshkar.authorssystem.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
public class Category {
    private static long nextId = 1L;

    private final Long id;
    @Setter
    private String title;

    public  Category() {
        this.id = nextId++;

    }
    public  Category(String title) {
        this.id = nextId++;
        this.title = title;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Category))
            return false;
        return this.id.equals(((Category)obj).id);
    }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", title='" + title + '\'' +
                '}';
    }
}
