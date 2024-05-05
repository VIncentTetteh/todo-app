package com.chrisbone.todolist.v1.utils;

import com.chrisbone.todolist.v1.configs.BaseEntity;
import jakarta.persistence.PrePersist;

import java.time.LocalDateTime;

public class EntityListener {
    @PrePersist
    public void onPrePersist(Object entity) {
        if (entity instanceof BaseEntity) {
            BaseEntity baseEntity = (BaseEntity) entity;
            baseEntity.setCreatedAt(LocalDateTime.now());
        }
    }
}
