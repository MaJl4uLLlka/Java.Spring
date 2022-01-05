package com.example.mainspingproject.exceptions;

import javax.security.auth.login.LoginException;


public class UniqueEmailException extends LoginException {
    public UniqueEmailException() {
    }

    public UniqueEmailException(String msg) {
        super(msg);
    }
}
