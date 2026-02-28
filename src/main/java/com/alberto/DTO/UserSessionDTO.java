package com.alberto.DTO;

import java.io.Serializable;

public class UserSessionDTO implements Serializable {

    private Long userId;
    private String username;

    public UserSessionDTO(Long userId, String username) {
        this.userId = userId;
        this.username = username;
    }

    public Long getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }
}