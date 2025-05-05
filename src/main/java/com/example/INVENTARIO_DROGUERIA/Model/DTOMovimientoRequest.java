package com.example.INVENTARIO_DROGUERIA.Model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class DTOMovimientoRequest {
    private String codigoProducto; // ya
    private Integer cantidad; // ya
    private String motivo; // ya
    private String observaciones; // ya
    private MovimientoInventario.TipoMovimiento tipo; // ya
    // Sugerir el precio en el front, ya que puede venderse o comprarse por otro precio (front)
    private BigDecimal precioCompraVenta; // ya
    // Se requiere en caso de entrada, para productos controlados por lote
    private DTOLoteRequest lote; // ya establecido
    // Se requiere en caso de salida, para productos controlados por lote
    private String numeroLote;
}
