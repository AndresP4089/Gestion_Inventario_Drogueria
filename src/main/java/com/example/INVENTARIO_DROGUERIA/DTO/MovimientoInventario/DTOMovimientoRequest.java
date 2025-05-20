package com.example.INVENTARIO_DROGUERIA.DTO.MovimientoInventario;

import com.example.INVENTARIO_DROGUERIA.DTO.Lote.DTOLoteRequest;
import com.example.INVENTARIO_DROGUERIA.Model.MovimientoInventario;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class DTOMovimientoRequest {
    private String codigoProducto;
    private Integer cantidad;
    private String motivo;
    private String observaciones;
    private MovimientoInventario.TipoMovimiento tipo;
    // Sugerir el precio en el front, ya que puede venderse o comprarse por otro precio (front)
    private BigDecimal precioCompraVenta;
    // Se requiere en caso de entrada, para productos controlados por lote
    private DTOLoteRequest lote;
    // Se requiere en caso de salida, para productos controlados por lote
    private String numeroLote;
}
