package com.chrisbone.todolist.v1.dto.requests;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;

public record UserRequestDTO(
        @NotEmpty(message = "First name can not be empty.")
       String firstName,
        @NotEmpty(message = "Last name can not be empty.")
       String lastName,
        @NotEmpty(message = "Username can not be empty.")
       String username,
        @NotEmpty(message = "Email can not be empty.")
       String email,
        @NotEmpty(message = "Password can not be empty.")
        @Min(value = 8,message = "Password must be at least 8 character or more.")
       String password,
        @NotEmpty(message = "Mobile number can not be empty.")
       String mobileNumber,
       String profileImageUrl
        ) {
}
