package com.chrisbone.todolist.v1.serviceImpls;

import com.chrisbone.todolist.v1.dto.objectMappers.CategoryMapper;
import com.chrisbone.todolist.v1.dto.requests.CategoryRequestDTO;
import com.chrisbone.todolist.v1.dto.responses.CategoryResponseDTO;
import com.chrisbone.todolist.v1.exceptions.CategoryAlreadyExistException;
import com.chrisbone.todolist.v1.exceptions.CategoryNotFoundException;
import com.chrisbone.todolist.v1.exceptions.UserNotFoundException;
import com.chrisbone.todolist.v1.models.Category;
import com.chrisbone.todolist.v1.repositories.CategoryRepository;
import com.chrisbone.todolist.v1.services.CategoryService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    private ModelMapper modelMapper;
    @Override
    public CategoryResponseDTO createCategory(CategoryRequestDTO categoryRequestDTO) {
        if(categoryRepository.existsByName(categoryRequestDTO.name())){
            throw new CategoryAlreadyExistException("category already exist");
        }
        Category category = categoryMapper.convertToCategory(categoryRequestDTO);
        category.setCreatedBy("");
        Category savedCategory = categoryRepository.save(category);
        return modelMapper.map(savedCategory,CategoryResponseDTO.class);
    }

    @Override
    public CategoryResponseDTO updateCategory(UUID id, CategoryRequestDTO categoryRequestDTO) {
        Category existingCategory = categoryRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("category not found with id: " + id));
        modelMapper.map(categoryRequestDTO,existingCategory);
        existingCategory.setUpdatedAt(LocalDateTime.now());
        Category updatedCategory = categoryRepository.save(existingCategory);

        return modelMapper.map(updatedCategory,CategoryResponseDTO.class);
    }

    @Override
    public void deleteCategory(UUID id) {
        Category existingCategory = categoryRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("category not found with id: " + id));

        categoryRepository.deleteById(id);
    }

    @Override
    public CategoryResponseDTO getCategoryById(UUID id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException("category not found with id: " + id));
        return modelMapper.map(category,CategoryResponseDTO.class);
    }

    @Override
    public List<CategoryResponseDTO> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream()
                .map(category -> modelMapper.map(category, CategoryResponseDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<CategoryResponseDTO> getCategoriesByUserId(UUID id) {
        List<Category> categories = categoryRepository.findByUserId(id);
        return categories.stream()
                .map(category -> modelMapper.map(category, CategoryResponseDTO.class))
                .collect(Collectors.toList());
    }
}
