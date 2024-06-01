package com.chrisbone.todolist.v1.repositories;

import com.chrisbone.todolist.v1.models.Category;
import com.chrisbone.todolist.v1.models.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CategoryRepository extends JpaRepository<Category, UUID> {
    List<Category> findByUserId(UUID id);
    boolean existsByName(String name);
    Optional<Category> findByIdAndUserId(UUID categoryId, UUID userId);
    boolean existsByNameAndUserId(String name, UUID user_id);

}
