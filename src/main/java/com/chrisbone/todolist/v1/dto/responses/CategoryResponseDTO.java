package com.chrisbone.todolist.v1.dto.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CategoryResponseDTO extends BaseResponse{
        @JsonProperty("categoryName")
        private String name;
        @JsonProperty("createdAt")
        private LocalDateTime createdAt;

        @JsonProperty("updatedAt")
        private LocalDateTime updatedAt;

}
