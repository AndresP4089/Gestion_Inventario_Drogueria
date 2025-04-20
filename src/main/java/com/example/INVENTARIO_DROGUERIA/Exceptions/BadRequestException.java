package com.example.INVENTARIO_DROGUERIA.Exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BadRequestException extends RuntimeException{
    private final String mensaje;
}
