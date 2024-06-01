package com.chrisbone.todolist.v1.serviceImpls;

import com.chrisbone.todolist.v1.dto.objectMappers.TodoMapper;
import com.chrisbone.todolist.v1.dto.requests.TodoRequestDTO;
import com.chrisbone.todolist.v1.dto.responses.TodoResponseDTO;
import com.chrisbone.todolist.v1.enums.Status;
import com.chrisbone.todolist.v1.exceptions.*;
import com.chrisbone.todolist.v1.models.Category;
import com.chrisbone.todolist.v1.models.Todo;
import com.chrisbone.todolist.v1.models.User;
import com.chrisbone.todolist.v1.repositories.CategoryRepository;
import com.chrisbone.todolist.v1.repositories.TodoRepository;
import com.chrisbone.todolist.v1.repositories.UserRepository;
import com.chrisbone.todolist.v1.services.TodoService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class TodoServiceImpl implements TodoService {

    private final TodoRepository todoRepository;
    private final TodoMapper todoMapper;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    ModelMapper modelMapper = new ModelMapper();
    @Override
    public TodoResponseDTO createTodo(TodoRequestDTO todoRequestDTO) {

        if(!userRepository.existsById(todoRequestDTO.user_id())){
            throw new UserNotFoundException("user does not exist");
        }

        if(todoRepository.existsByTitle(todoRequestDTO.title())){
            throw new CategoryAlreadyExistException("todo already exist");
        }

        if(!categoryRepository.existsById(todoRequestDTO.category_id())){
            throw new CategoryAlreadyExistException("category does not exist");
        }

        User user = userRepository.findById(todoRequestDTO.user_id())
                .orElseThrow(() -> new UserNotFoundException("User does not exist"));
        Category category = categoryRepository.findById(todoRequestDTO.category_id())
                .orElseThrow(() -> new CategoryNotFoundException("category does not exists"));

        Todo todo = todoMapper.convertToTodo(todoRequestDTO);

        todo.setUser(user);
        todo.setStatus(Status.PENDING);
        todo.setCategory(category);

        Todo savedTodo = todoRepository.save(todo);
        return modelMapper.map(savedTodo, TodoResponseDTO.class);
    }

    @Override
    public TodoResponseDTO updateTodo(UUID todoId, TodoRequestDTO todoRequestDTO) {
        Todo todoExists = todoRepository.findByIdAndUserId(todoId,todoRequestDTO.user_id())
                .orElseThrow(() -> new TodoNotFoundException("todo not found with id: " + todoId));


        modelMapper.map(todoRequestDTO,todoExists);
        todoExists.setUpdatedAt(LocalDateTime.now());
        Todo updatedTodo = todoRepository.save(todoExists);

        return modelMapper.map(updatedTodo,TodoResponseDTO.class);
    }

    @Override
    public void deleteTodo(UUID todoId,UUID userId) {
        Todo todoExists = todoRepository.findByIdAndUserId(todoId,userId)
                .orElseThrow(() -> new TodoNotFoundException("todo not found with id: " + todoId));

        todoRepository.deleteById(todoId);
    }

    @Override
    public TodoResponseDTO getTodoById(UUID todoId,UUID userId) {
        Todo todo = todoRepository.findByIdAndUserId(todoId,userId)
                .orElseThrow(() -> new TodoNotFoundException("todo not found with id: " + todoId));

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
    public List<TodoResponseDTO> getTodosByUserId(UUID userId) {
        List<Todo> todos = todoRepository.findByUserId(userId);
        return todos.stream()
                .map(todo -> modelMapper.map(todo, TodoResponseDTO.class))
                .collect(Collectors.toList());
    }


    @Override
    public void partialUpdateTodo(UUID todoId,UUID userId, Map<String, Object> updates) {
        Todo existingTodo = todoRepository.findByIdAndUserId(todoId,userId)
                .orElseThrow(() -> new TodoNotFoundException("Todo not found with id: " + todoId));

        // Apply partial updates to existing todo
        applyPartialUpdates(existingTodo, updates);

        // Save the updated todo
        todoRepository.save(existingTodo);
    }

    private void applyPartialUpdates(Todo todo, Map<String, Object> updates) {
        // Apply updates using BeanUtils from Spring
        BeanUtils.copyProperties(updates, todo, "id", "createdBy", "createdAt", "updatedAt");
    }
}
