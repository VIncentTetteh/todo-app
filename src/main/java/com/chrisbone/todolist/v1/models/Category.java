package com.chrisbone.todolist.v1.models;

import com.chrisbone.todolist.v1.configs.BaseEntity;
import com.chrisbone.todolist.v1.utils.EntityListener;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "Category")
@EntityListeners(EntityListener.class)
public class Category extends BaseEntity {

    @NotNull(message = "category name can not be null")
    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Todo> todoList;

    @ManyToOne
    @JoinColumn(name = "user_id",referencedColumnName = "id")
    private User user;
}
