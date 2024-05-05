package com.chrisbone.todolist.v1.serviceImpls;

import com.chrisbone.todolist.v1.dto.objectMappers.TodoMapper;
import com.chrisbone.todolist.v1.dto.requests.TodoRequestDTO;
import com.chrisbone.todolist.v1.dto.responses.TodoResponseDTO;
import com.chrisbone.todolist.v1.exceptions.*;
import com.chrisbone.todolist.v1.models.Todo;
import com.chrisbone.todolist.v1.repositories.TodoRepository;
import com.chrisbone.todolist.v1.services.TodoService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class TodoServiceImpl implements TodoService {

    private final TodoRepository todoRepository;
    private final TodoMapper todoMapper;
    private ModelMapper modelMapper;
    @Override
    public TodoResponseDTO createTodo(TodoRequestDTO todoRequestDTO) {
        if(todoRepository.existsByTitle(todoRequestDTO.title())){
            throw new TodoAlreadyExistsException("todo already exist");
        }
        Todo todo = todoMapper.convertToTodo(todoRequestDTO);
        todo.setCreatedBy("");
        Todo savedTodo = todoRepository.save(todo);
        return modelMapper.map(savedTodo, TodoResponseDTO.class);
    }

    @Override
    public TodoResponseDTO updateTodo(UUID id, TodoRequestDTO todoRequestDTO) {
        Todo existingTodo = todoRepository.findById(id)
                .orElseThrow(() -> new TodoNotFoundException("todo not found with id: " + id));
        modelMapper.map(todoRequestDTO,existingTodo);
        existingTodo.setUpdatedAt(LocalDateTime.now());
        Todo updatedTodo = todoRepository.save(existingTodo);

        return modelMapper.map(updatedTodo,TodoResponseDTO.class);
    }

    @Override
    public void deleteTodo(UUID id) {
        Todo existingTodo = todoRepository.findById(id)
                .orElseThrow(() -> new TodoNotFoundException("todo not found with id: " + id));

        todoRepository.deleteById(id);
    }

    @Override
    public TodoResponseDTO getTodoById(UUID id) {
        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new TodoNotFoundException("todo not found with id: " + id));
        return modelMapper.map(todo,TodoResponseDTO.class);
    }

    @Override
    public List<TodoResponseDTO> getAllTodos() {
        List<Todo> todos = todoRepository.findAll();
        return todos.stream()
                .map(todo -> modelMapper.map(todo, TodoResponseDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<TodoResponseDTO> getTodosByUserId(UUID id) {
        List<Todo> todos = todoRepository.findByUserId(id);
        return todos.stream()
                .map(todo -> modelMapper.map(todo, TodoResponseDTO.class))
                .collect(Collectors.toList());
    }
}
