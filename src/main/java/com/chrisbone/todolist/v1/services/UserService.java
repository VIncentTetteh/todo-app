package com.chrisbone.todolist.v1.services;

import com.chrisbone.todolist.v1.dto.requests.UserRequestDTO;
import com.chrisbone.todolist.v1.dto.responses.UserResponseDTO;
import com.chrisbone.todolist.v1.models.User;

import java.util.List;
import java.util.UUID;

public interface UserService {
    UserResponseDTO createUser(UserRequestDTO userRequestDTO);
    UserResponseDTO updateUser(UUID id, UserRequestDTO userRequestDTO);
    void deleteUser(UUID id);
    UserResponseDTO getUserById(UUID id);
    List<UserResponseDTO> getAllUsers();
}
