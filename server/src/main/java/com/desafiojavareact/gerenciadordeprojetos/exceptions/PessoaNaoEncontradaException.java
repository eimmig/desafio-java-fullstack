package com.desafiojavareact.gerenciadordeprojetos.exceptions;

public class PessoaNaoEncontradaException extends RuntimeException {

    public PessoaNaoEncontradaException() {
        super();
    }

    public PessoaNaoEncontradaException(String message) {
        super(message);
    }

    public PessoaNaoEncontradaException(String message, Throwable cause) {
        super(message, cause);
    }

    public PessoaNaoEncontradaException(Throwable cause) {
        super(cause);
    }
}