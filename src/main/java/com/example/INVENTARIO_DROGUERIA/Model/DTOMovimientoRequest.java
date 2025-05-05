package com.example.INVENTARIO_DROGUERIA.Model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class DTOMovimientoRequest {
    private String codigoProducto;
    private String numeroLote;
    private Integer cantidad;

    // Obtener del producto, pero como sugerencia(en el front)
    private BigDecimal precioCompraVenta;

    // Obtener de time(en el servicio)
    //private LocalDate fecha;

    private String motivo;
    private String observaciones;
    private MovimientoInventario.TipoMovimiento tipo;
}
