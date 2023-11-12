package com.desafiojavareact.gerenciadordeprojetos.exceptions;

public class ProjetoJaIniciadoException extends RuntimeException {

    public ProjetoJaIniciadoException() {
        super();
    }

    public ProjetoJaIniciadoException(String message) {
        super(message);
    }

    public ProjetoJaIniciadoException(String message, Throwable cause) {
        super(message, cause);
    }

    public ProjetoJaIniciadoException(Throwable cause) {
        super(cause);
    }
}