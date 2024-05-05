package com.chrisbone.todolist.v1.models;

import com.chrisbone.todolist.v1.configs.BaseEntity;
import com.chrisbone.todolist.v1.enums.Status;
import com.chrisbone.todolist.v1.utils.EntityListener;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "Todo")
@EntityListeners(EntityListener.class)
public class Todo extends BaseEntity {

    @NotNull(message = "title can not be empty")
    @Column(name = "title")
    private String title;

    @NotNull(message = "description date can not be empty")
    @Column(name = "description")
    private String description;

    @NotNull(message = "due date can not be empty")
    @Column(name = "due_date")
    private LocalDateTime dueDate;

    @NotNull(message = "status date can not be empty")
    @Column(name = "status")
    private Status status;

    @NotNull(message = "priority level can not be empty")
    @Column(name = "priority_level")
    private int priorityLevel;

    @NotNull(message = "created by can not be empty")
    @Column(name = "created_by", nullable = false)
    private String createdBy;

    @ManyToOne
    @JoinColumn(name = "user_id",referencedColumnName = "id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "category_id",referencedColumnName = "id")
    private Category category;
}
