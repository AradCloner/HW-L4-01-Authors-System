package net.daneshkar.authorssystem.repository;

import net.daneshkar.authorssystem.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Long> {
    Author findByUsername(String username);
}
