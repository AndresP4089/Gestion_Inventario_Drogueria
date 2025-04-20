package com.example.INVENTARIO_DROGUERIA.Exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class NotFoundDataException extends RuntimeException {
    private final String mensaje;
}
