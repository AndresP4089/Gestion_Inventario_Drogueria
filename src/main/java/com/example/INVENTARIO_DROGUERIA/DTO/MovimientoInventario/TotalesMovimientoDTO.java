package com.example.INVENTARIO_DROGUERIA.DTO.MovimientoInventario;

import com.example.INVENTARIO_DROGUERIA.Model.MovimientoInventario;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TotalesMovimientoDTO {
    // Movimientos
    private int totalEntradas;
    private int totalSalidas;
    private int totalMovimientos;
    private int totalNeto;

    // Calcular los totales de entradas y salidas
    public static TotalesMovimientoDTO calcularTotalesMovimientos(List<MovimientoReporteDTO> movimientos) {
        int totalEntradas = 0;
        int totalSalidas = 0;

        for (MovimientoReporteDTO movimiento : movimientos) {
            if (MovimientoInventario.TipoMovimiento.ENTRADA.equals(movimiento.getTipo())) {
                totalEntradas += movimiento.getCantidad();
            } else {
                totalSalidas += movimiento.getCantidad();
            }
        }

        int totalNeto = totalEntradas - totalSalidas;
        int totalMovimientos = totalEntradas + totalSalidas;

        return new TotalesMovimientoDTO(totalEntradas, totalSalidas, totalMovimientos, totalNeto);
    }
}
