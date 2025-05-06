package com.example.INVENTARIO_DROGUERIA.Model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class MovimientoFiltroDTO {
    private LocalDate fechaDesde;     // para rango
    private LocalDate fechaHasta;     // para rango
    private LocalDate fechaExacta;    // alternativa si solo quieres una fecha exacta
    private MovimientoInventario.TipoMovimiento tipoMovimiento;
    private String codigoProducto;    // opcional: filtrar por producto
}

