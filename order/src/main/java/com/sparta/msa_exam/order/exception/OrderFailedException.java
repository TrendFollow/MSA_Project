package com.sparta.msa_exam.order.exception;

public class OrderFailedException extends RuntimeException {

    public OrderFailedException(String message) {
        super(message);
    }

}
