package com.chrisbone.todolist.v1.dto.objectMappers;

import com.chrisbone.todolist.v1.dto.requests.CategoryRequestDTO;
import com.chrisbone.todolist.v1.dto.requests.UserRequestDTO;
import com.chrisbone.todolist.v1.models.Category;
import com.chrisbone.todolist.v1.models.User;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {

    public Category convertToCategory(CategoryRequestDTO categoryRequestDTO){
        return Category.builder()
                .name(categoryRequestDTO.name())
                .build();
    }
}
