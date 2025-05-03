package com.example.INVENTARIO_DROGUERIA.Model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class DTOLoteRequest {
    private String numeroLote;
    private LocalDate fechaVencimiento;
    private Integer cantidadInicial;
    private String codigoProducto;
    private String nitProveedor;
}
