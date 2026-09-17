package com.robin.msvc_man_socio.exception;

import lombok.Getter;

@Getter
public class SocioDuplicadoException extends RuntimeException {
    private final String campo;
    private final String valor;

    public SocioDuplicadoException(String campo, String valor) {
        super(String.format("Ya existe un socio con el %s: %s", campo, valor));
        this.campo = campo;
        this.valor = valor;
    }

    public SocioDuplicadoException(String mensaje) {
        super(mensaje);
        this.campo = null;
        this.valor = null;
    }
}
