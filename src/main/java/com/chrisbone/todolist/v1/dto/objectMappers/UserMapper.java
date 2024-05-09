package com.chrisbone.todolist.v1.dto.objectMappers;

import com.chrisbone.todolist.v1.dto.requests.UserRequestDTO;
import com.chrisbone.todolist.v1.enums.Role;
import com.chrisbone.todolist.v1.models.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMapper {
    private final PasswordEncoder passwordEncoder;
    public User convertToUser(UserRequestDTO userRequestDTO){
        return User.builder()
                .email(userRequestDTO.email())
                .firstName(userRequestDTO.firstName())
                .lastName(userRequestDTO.lastName())
                .username(userRequestDTO.username())
                .roles(Role.ROLE_USER)
                .password(passwordEncoder.encode(userRequestDTO.password()))
                .mobileNumber(userRequestDTO.mobileNumber())
                .profileImageUrl(userRequestDTO.profileImageUrl())
                .build();

    }
}
