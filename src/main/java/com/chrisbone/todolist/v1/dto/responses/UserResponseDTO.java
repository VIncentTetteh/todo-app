package com.chrisbone.todolist.v1.dto.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDTO extends BaseResponse{
        @JsonProperty("userId")
        private UUID id;

        @JsonProperty("userName")
        private String username;
        @JsonProperty("userFirstName")
        private String firstName;
        @JsonProperty("userLastName")
        private String lastName;

        @JsonProperty("userEmail")
        private String email;

}
