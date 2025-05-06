package com.example.INVENTARIO_DROGUERIA.DTO.MovimientoInventario;

import com.example.INVENTARIO_DROGUERIA.Model.MovimientoInventario;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class MovimientoReporteDTO {

    // Datos de movimiento
    private Long id;
    private MovimientoInventario.TipoMovimiento tipo; // ENTRADA o SALIDA
    private Integer cantidad;
    private BigDecimal precioCompraVenta;
    private LocalDate fecha;
    private String motivo;
    private String observaciones;

    // Datos del producto
    private String codigoProducto;
    private String nombreProducto;

    // Datos del lote (si aplica)
    private String numeroLote;
    private LocalDate fechaVencimientoLote;

    // Datos del proveedor
    private String nombreProveedor;
}

