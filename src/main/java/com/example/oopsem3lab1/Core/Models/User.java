package com.example.oopsem3lab1.Core.Models;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class User {
    private String username;
    private String name;

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
