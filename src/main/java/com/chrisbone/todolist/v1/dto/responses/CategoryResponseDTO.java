package com.chrisbone.todolist.v1.dto.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CategoryResponseDTO extends BaseResponse{

        @JsonProperty("categoryID")
        private UUID id;

        @JsonProperty("categoryName")
        private String name;

        @JsonProperty("createdAt")
        private LocalDateTime createdAt;

        @JsonProperty("updatedAt")
        private LocalDateTime updatedAt;

        private List<TodoResponseDTO> todoList;

}
