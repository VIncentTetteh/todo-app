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
public class TodoResponseDTO extends BaseResponse{

        @JsonProperty("todoID")
        private UUID id;

        @JsonProperty("todoTitle")
        private String title;

        @JsonProperty("todoDescription")
        private String description;

        @JsonProperty("todoDueDate")
        private LocalDateTime dueDate;

        @JsonProperty("todoStatus")
        private String status;

        @JsonProperty("todoPriorityLevel")
        private int priorityLevel;

        @JsonProperty("createdAt")
        private LocalDateTime createdAt;

        @JsonProperty("updatedAt")
        private LocalDateTime updatedAt;


}
