package com.collins.leftover.dto.user;

public class AuthResponseDto {
    private Long id;
    private String name;

    public AuthResponseDto(){}

    public AuthResponseDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
