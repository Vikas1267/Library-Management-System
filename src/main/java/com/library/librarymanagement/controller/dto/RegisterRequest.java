package com.library.librarymanagement.controller.dto;

import com.library.librarymanagement.entity.Role;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequest {

    private String name;
    private String email;
    private String password;
    private Role role; // LIBRARIAN or MEMBER
}
