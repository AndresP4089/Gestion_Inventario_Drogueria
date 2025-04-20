package com.example.INVENTARIO_DROGUERIA.Exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class NoContentData extends RuntimeException {
    private final String mensaje;
}
