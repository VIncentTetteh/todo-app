package com.chrisbone.todolist.v1.serviceImpls;

import com.chrisbone.todolist.v1.dto.objectMappers.UserMapper;
import com.chrisbone.todolist.v1.dto.requests.UserRequestDTO;
import com.chrisbone.todolist.v1.dto.responses.UserResponseDTO;
import com.chrisbone.todolist.v1.exceptions.TodoNotFoundException;
import com.chrisbone.todolist.v1.exceptions.UserAlreadyExistsException;
import com.chrisbone.todolist.v1.exceptions.UserNotFoundException;
import com.chrisbone.todolist.v1.models.Todo;
import com.chrisbone.todolist.v1.models.User;
import com.chrisbone.todolist.v1.repositories.UserRepository;
import com.chrisbone.todolist.v1.services.UserService;
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
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private ModelMapper modelMapper;
    @Override
    public UserResponseDTO createUser(UserRequestDTO userRequestDTO) {

        if (userRepository.existsByEmail(userRequestDTO.email()) || userRepository.existsByUsername(userRequestDTO.username())) {
            throw new UserAlreadyExistsException("User with the same email or username already exists");
        }

        User user = userMapper.convertToUser(userRequestDTO);
        User savedUser = userRepository.save(user);
        return modelMapper.map(savedUser,UserResponseDTO.class);
    }

    @Override
    public UserResponseDTO updateUser(UUID id, UserRequestDTO userRequestDTO) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));
        modelMapper.map(userRequestDTO,existingUser);
        existingUser.setUpdatedAt(LocalDateTime.now());
        User updatedUser = userRepository.save(existingUser);

        return modelMapper.map(updatedUser,UserResponseDTO.class);
    }

    @Override
    public void deleteUser(UUID id) {
        userRepository.deleteById(id);
    }

    @Override
    public UserResponseDTO getUserById(UUID id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));
        return modelMapper.map(user, UserResponseDTO.class);
    }

    @Override
    public List<UserResponseDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(user -> modelMapper.map(user,UserResponseDTO.class))
                .collect(Collectors.toList());
    }


    @Override
    public void partialUpdateUser(UUID id, Map<String, Object> updates) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new TodoNotFoundException("Todo not found with id: " + id));

        // Apply partial updates to existing user
        applyPartialUpdates(existingUser, updates);

        // Save the updated todo
        userRepository.save(existingUser);
    }

    private void applyPartialUpdates(User user, Map<String, Object> updates) {
        // Apply updates using BeanUtils from Spring
        BeanUtils.copyProperties(updates, user, "id", "createdBy", "createdAt", "updatedAt","email");
    }
}
