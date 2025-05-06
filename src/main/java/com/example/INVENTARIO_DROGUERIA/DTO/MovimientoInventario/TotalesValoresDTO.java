package com.example.INVENTARIO_DROGUERIA.DTO.MovimientoInventario;

import com.example.INVENTARIO_DROGUERIA.Model.MovimientoInventario;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TotalesValoresDTO {
    // Valores
    private BigDecimal valorTotalEntradas;
    private BigDecimal valorTotalSalidas;
    private BigDecimal valorTotal;
    private BigDecimal valorNeto;

    // Calcular los totales de precios
    public static TotalesValoresDTO calcularTotalesValores(List<MovimientoReporteDTO> movimientos) {
        BigDecimal valorTotalEntradas = BigDecimal.ZERO;
        BigDecimal valorTotalSalidas = BigDecimal.ZERO;

        for (MovimientoReporteDTO movimiento : movimientos) {
            if (MovimientoInventario.TipoMovimiento.ENTRADA.equals(movimiento.getTipo())) {
                valorTotalEntradas = valorTotalEntradas.add(movimiento.getPrecioCompraVenta());
            } else {
                valorTotalSalidas =valorTotalSalidas.add(movimiento.getPrecioCompraVenta());
            }
        }

        BigDecimal valorNeto = valorTotalEntradas.subtract(valorTotalSalidas);
        BigDecimal valorTotal = valorTotalEntradas.add(valorTotalSalidas);

        return new TotalesValoresDTO(valorTotalEntradas, valorTotalSalidas, valorTotal, valorNeto);
    }
}
