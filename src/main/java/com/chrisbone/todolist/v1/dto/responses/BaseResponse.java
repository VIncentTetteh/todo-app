package com.chrisbone.todolist.v1.dto.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.time.LocalDateTime;


@Getter
@Setter
public class BaseResponse{
        @JsonProperty("createdAt")
        private LocalDateTime createdAt;

        @JsonProperty("updatedAt")
        private LocalDateTime updatedAt;
}
