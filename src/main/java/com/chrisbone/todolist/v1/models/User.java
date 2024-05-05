package com.chrisbone.todolist.v1.models;

import com.chrisbone.todolist.v1.configs.BaseEntity;
import com.chrisbone.todolist.v1.enums.Role;
import com.chrisbone.todolist.v1.utils.EntityListener;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "Users")
@EntityListeners(EntityListener.class)
public class User extends BaseEntity {

    @NotNull(message = "first name can not be empty")
    @Column(name = "first_name")
    private String firstName;

    @NotNull(message = "last name can not be empty")
    @Column(name = "last_name")
    private String lastName;

    @Column(name = "username")
    private String username;

    @Email(message = "provide a valid email")
    @NotNull(message = "email can not be empty")
    @Column(name = "email",unique = true)
    private String email;

    @NotNull(message = "password can not be empty")
    @Column(name = "password",length = 14)
    private String password;

    @NotNull(message = "mobile number can not be empty")
    @Column(name = "mobile_number")
    private String mobileNumber;

    @NotNull(message = "role must be attached")
    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private Role roles;

    @Column(name = "profile_image_url")
    private String profileImageUrl;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Todo> todoList;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Category> categories;

}
