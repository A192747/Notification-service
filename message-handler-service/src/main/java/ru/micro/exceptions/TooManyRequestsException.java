package ru.micro.exceptions;

public class TooManyRequestsException extends RuntimeException {
    public TooManyRequestsException(String msg) {
        super(msg);
    }
}
