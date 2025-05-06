package com.example.INVENTARIO_DROGUERIA.DTO.MovimientoInventario;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ResumenMovimientoInventarioDTO {
    private List<MovimientoReporteDTO> movimientos;
    private TotalesMovimientoDTO totalesMovimientos;
    private TotalesValoresDTO totalesValores;
}

