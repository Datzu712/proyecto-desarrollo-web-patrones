package com.dream.restapi.dto;

import lombok.Data;

@Data
public class AuthRegisterBody {
    private String email;
    private String password;
    private String name;
}
