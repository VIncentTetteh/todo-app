package com.chrisbone.todolist.v1.serviceImpls;

import com.chrisbone.todolist.v1.dto.objectMappers.CategoryMapper;
import com.chrisbone.todolist.v1.dto.requests.CategoryRequestDTO;
import com.chrisbone.todolist.v1.dto.responses.CategoryResponseDTO;
import com.chrisbone.todolist.v1.exceptions.CategoryAlreadyExistException;
import com.chrisbone.todolist.v1.exceptions.CategoryNotFoundException;
import com.chrisbone.todolist.v1.exceptions.TodoNotFoundException;
import com.chrisbone.todolist.v1.exceptions.UserNotFoundException;
import com.chrisbone.todolist.v1.models.Category;
import com.chrisbone.todolist.v1.models.Todo;
import com.chrisbone.todolist.v1.models.User;
import com.chrisbone.todolist.v1.repositories.CategoryRepository;
import com.chrisbone.todolist.v1.repositories.TodoRepository;
import com.chrisbone.todolist.v1.repositories.UserRepository;
import com.chrisbone.todolist.v1.services.CategoryService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final TodoRepository todoRepository;
    private final CategoryMapper categoryMapper;
    ModelMapper modelMapper = new ModelMapper();
    @Override
    public CategoryResponseDTO createCategory(CategoryRequestDTO categoryRequestDTO) {


        User user = userRepository.findById(categoryRequestDTO.user_id())
                .orElseThrow(() -> new UserNotFoundException("User does not exist"));

        boolean categoryExists = categoryRepository.existsByNameAndUserId(categoryRequestDTO.name(), categoryRequestDTO.user_id());

        if (categoryExists) {
            throw new CategoryAlreadyExistException("Category already exists for this user");
        }

        Category category = categoryMapper.convertToCategory(categoryRequestDTO);

        category.setUser(user);
        Category savedCategory = categoryRepository.save(category);
        return modelMapper.map(savedCategory,CategoryResponseDTO.class);
    }

    @Override
    public CategoryResponseDTO updateCategory(UUID categoryId, CategoryRequestDTO categoryRequestDTO) {
        Category existingCategory = categoryRepository.findByIdAndUserId(categoryId,categoryRequestDTO.user_id())
                .orElseThrow(() -> new CategoryNotFoundException("category not found with id: " + categoryId));

        modelMapper.map(categoryRequestDTO,existingCategory);
        existingCategory.setUpdatedAt(LocalDateTime.now());
        Category updatedCategory = categoryRepository.save(existingCategory);

        return modelMapper.map(updatedCategory,CategoryResponseDTO.class);
    }

    @Override
    public void deleteCategory(UUID categoryId, UUID user_id) {
        Category existingCategory = categoryRepository.findByIdAndUserId(categoryId,user_id)
                .orElseThrow(() -> new UserNotFoundException("category not found with id: " + categoryId));

        categoryRepository.deleteById(categoryId);
    }

    @Override
    public CategoryResponseDTO getCategoryById(UUID categoryId,UUID user_id) {
        Category existingCategory = categoryRepository.findByIdAndUserId(categoryId,user_id)
                .orElseThrow(() -> new CategoryNotFoundException("category not found with id: " + categoryId));


        return modelMapper.map(existingCategory,CategoryResponseDTO.class);
    }

    @Override
    public List<CategoryResponseDTO> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream()
                .map(category -> modelMapper.map(category, CategoryResponseDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<CategoryResponseDTO> getCategoriesByUserId(UUID userId) {
        List<Category> categories = categoryRepository.findByUserId(userId);
        return categories.stream()
                .map(category -> modelMapper.map(category, CategoryResponseDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public void partialUpdateCategory(UUID categoryId,UUID user_id, Map<String, Object> updates) {
        Category existingCategory = categoryRepository.findByIdAndUserId(categoryId,user_id)
                .orElseThrow(() -> new TodoNotFoundException("Todo not found with id: " + categoryId));

        // Apply partial updates to existing category
        applyPartialUpdates(existingCategory, updates);

        // Save the updated todo
        categoryRepository.save(existingCategory);
    }

    private void applyPartialUpdates(Category category, Map<String, Object> updates) {
        // Apply updates using BeanUtils from Spring
        BeanUtils.copyProperties(updates, category, "id", "createdBy", "createdAt", "updatedAt");
    }
}
