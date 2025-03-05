package com.example.API.Test.Exception;

public class TaskNotFoundException  extends RuntimeException {
	public TaskNotFoundException(String message) {
        super(message);
    }
}
