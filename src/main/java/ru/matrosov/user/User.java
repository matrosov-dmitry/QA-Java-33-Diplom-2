package ru.matrosov.user;

import lombok.Data;

@Data
public class User
    {
        public final String name;
        public final String password;
        public final String email;
    }
