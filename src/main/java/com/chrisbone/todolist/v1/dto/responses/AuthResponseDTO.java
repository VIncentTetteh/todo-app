package com.chrisbone.todolist.v1.dto.responses;

import com.chrisbone.todolist.v1.enums.TokenType;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponseDTO {

    @JsonProperty("access_token")
    private String accessToken;

    @JsonProperty("access_token_expiry")
    private int accessTokenExpiry;

    @JsonProperty("token_type")
    private TokenType tokenType;

    @JsonProperty("user_id")
    private UUID id;
}
