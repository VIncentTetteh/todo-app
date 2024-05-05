package com.chrisbone.todolist.v1.repositories;

import com.chrisbone.todolist.v1.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CategoryRepository extends JpaRepository<Category, UUID> {
    List<Category> findByUserId(UUID id);
    boolean existsByName(String name);
}
