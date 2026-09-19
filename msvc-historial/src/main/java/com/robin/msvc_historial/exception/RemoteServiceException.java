package com.robin.msvc_historial.exception;

public class RemoteServiceException extends RuntimeException{
    private final int status;
    private final String codigo;

    public RemoteServiceException(String message, int status, String codigo) {
        super(message);
        this.status = status;
        this.codigo = codigo;
    }

}
