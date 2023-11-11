package com.desafiojavareact.gerenciadordeprojetos.exceptions;

public class ExclusaoDeUsuarioException extends RuntimeException {

    public ExclusaoDeUsuarioException() {
        super();
    }

    public ExclusaoDeUsuarioException(String message) {
        super(message);
    }

    public ExclusaoDeUsuarioException(String message, Throwable cause) {
        super(message, cause);
    }

    public ExclusaoDeUsuarioException(Throwable cause) {
        super(cause);
    }
}