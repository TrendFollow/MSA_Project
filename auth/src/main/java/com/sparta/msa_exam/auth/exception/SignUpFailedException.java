package com.sparta.msa_exam.auth.exception;

public class SignUpFailedException extends RuntimeException {
    public SignUpFailedException(String message) {
        super(message);
    }
}
