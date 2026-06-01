package com.pbl.IDE.dto;


import com.pbl.IDE.entity.User;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {

    private String username;
    private String email;
    private String fullName;
    private String containerID;

    public UserDTO() {
    }
    public UserDTO(User user) {
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.fullName = user.getFullName();
    }
}