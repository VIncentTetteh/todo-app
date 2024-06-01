package com.chrisbone.todolist.v1.repositories;

import com.chrisbone.todolist.v1.models.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TodoRepository extends JpaRepository<Todo, UUID> {
    List<Todo> findByUserId(UUID id);
    Optional<Todo> findByIdAndUserId(UUID todoId, UUID userId);

    boolean existsByTitle(String title);
}
