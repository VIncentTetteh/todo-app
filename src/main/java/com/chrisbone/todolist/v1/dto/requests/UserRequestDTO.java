package com.chrisbone.todolist.v1.dto.requests;

import com.chrisbone.todolist.v1.configs.ValidPassword;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

public record UserRequestDTO(
        @NotEmpty(message = "First name can not be empty.")
       String firstName,
        @NotEmpty(message = "Last name can not be empty.")
       String lastName,
        @NotEmpty(message = "Username can not be empty.")
       String username,
        @NotEmpty(message = "Email can not be empty.")
        @Email(regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}",flags = Pattern.Flag.CASE_INSENSITIVE)
       String email,
        @NotEmpty(message = "Password can not be empty.")
        @ValidPassword
       String password,
        @NotEmpty(message = "Mobile number can not be empty.")
       String mobileNumber,
       String profileImageUrl
        ) {
}
