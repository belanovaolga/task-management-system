package com.example.task.management.system.controller;

import com.example.task.management.system.exception.*;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

@RestControllerAdvice
public class ExceptionApiHandler {

    @ExceptionHandler(EmployeeAlreadyExists.class)
    public ResponseStatusException employeeAlreadyExists(EmployeeAlreadyExists employeeAlreadyExists) {
        return new ResponseStatusException(HttpStatusCode.valueOf(409), "The user already exists");
    }

    @ExceptionHandler(EmployeeNotFound.class)
    public ResponseStatusException employeeNotFound(EmployeeNotFound employeeNotFound) {
        return new ResponseStatusException(HttpStatusCode.valueOf(404), "The user does not exist");
    }

    @ExceptionHandler(PasswordOrEmailIncorrect.class)
    public ResponseStatusException passwordIncorrect(PasswordOrEmailIncorrect passwordOrEmailIncorrect) {
        return new ResponseStatusException(HttpStatusCode.valueOf(400), "The email or password is incorrect");
    }

    @ExceptionHandler(PasswordIncorrect.class)
    public ResponseStatusException passwordIncorrect(PasswordIncorrect passwordIncorrect) {
        return new ResponseStatusException(HttpStatusCode.valueOf(400), "The password is incorrect");
    }

    @ExceptionHandler(TaskNotFound.class)
    public ResponseStatusException taskNotFound(TaskNotFound taskNotFound) {
        return new ResponseStatusException(HttpStatusCode.valueOf(404), "The task does not exist");
    }

    @ExceptionHandler(CommentNotFound.class)
    public ResponseStatusException commentNotFound(CommentNotFound commentNotFound) {
        return new ResponseStatusException(HttpStatusCode.valueOf(404), "The comment does not exist");
    }
}

