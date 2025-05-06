package com.example.INVENTARIO_DROGUERIA.DTO.Lote;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class DTOLoteRequest {
    private String numeroLote;
    private LocalDate fechaVencimiento;
    private String codigoProducto;
    private String nitProveedor;
}
