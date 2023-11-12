package com.desafiojavareact.gerenciadordeprojetos.exceptions;

public class cpfInvalidoException extends RuntimeException {

    public cpfInvalidoException() {
        super();
    }

    public cpfInvalidoException(String message) {
        super(message);
    }

    public cpfInvalidoException(String message, Throwable cause) {
        super(message, cause);
    }

    public cpfInvalidoException(Throwable cause) {
        super(cause);
    }
}