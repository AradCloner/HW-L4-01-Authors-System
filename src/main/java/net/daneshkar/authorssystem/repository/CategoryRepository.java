package net.daneshkar.authorssystem.repository;

import net.daneshkar.authorssystem.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category,Long> {
    Optional<Category> findByTitle(String title);
}
