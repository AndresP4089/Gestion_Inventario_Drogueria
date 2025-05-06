package com.example.INVENTARIO_DROGUERIA.Controller;

import com.example.INVENTARIO_DROGUERIA.Exceptions.BadRequestException;
import com.example.INVENTARIO_DROGUERIA.Model.DTOMovimientoRequest;
import com.example.INVENTARIO_DROGUERIA.Model.MovimientoFiltroDTO;
import com.example.INVENTARIO_DROGUERIA.Model.MovimientoInventario;
import com.example.INVENTARIO_DROGUERIA.Model.MovimientoReporteDTO;
import com.example.INVENTARIO_DROGUERIA.Service.MovimientoInventarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/movimientos")
public class MovimientoInventarioController {

    @Autowired
    private MovimientoInventarioService movimientoService;

    // CONSULTAS

    // Listar por fecha
    @GetMapping("/paginar/{numeroPagina}/fecha")
    public ResponseEntity<Page<MovimientoInventario>> listarMovimientosPorFecha(@PathVariable int numeroPagina){

        if (numeroPagina<0) {
            throw new BadRequestException("No ingrese números negativos.");
        }

        return ResponseEntity.ok(movimientoService.listarPorFecha(PageRequest.of(numeroPagina, 10)));
    }

    // Listar por tipo
    @GetMapping("/paginar/{numeroPagina}/tipo/{tipo}")
    public ResponseEntity<Page<MovimientoInventario>> listarMovimientosPorTipo(@PathVariable int numeroPagina, @PathVariable MovimientoInventario.TipoMovimiento tipo) {

        if (numeroPagina<0) {
            throw new BadRequestException("No ingrese números negativos.");
        }

        return ResponseEntity.ok(movimientoService.listarPorTipo(tipo, PageRequest.of(numeroPagina,10)));
    }

    // Listar por lote
    @GetMapping("/paginar/{numeroPagina}/lote/{numeroLote}")
    public ResponseEntity<Page<MovimientoInventario>> listarMovimientosPorLote(@PathVariable int numeroPagina, @PathVariable String numeroLote) {

        if (numeroPagina<0) {
            throw new BadRequestException("No ingrese números negativos");
        }

        return ResponseEntity.ok(movimientoService.listarPorLote(numeroLote, PageRequest.of(numeroPagina, 10)));
    }

    // Listar por producto
    @GetMapping("/paginar/{numeroPagina}/producto/{codigoProducto}")
    public ResponseEntity<Page<MovimientoInventario>> listarMovimientosPorProducto(@PathVariable int numeroPagina, @PathVariable String codigoProducto) {

        if (numeroPagina<0) {
            throw new BadRequestException("No ingrese números negativos");
        }

        return ResponseEntity.ok(movimientoService.listarPorProducto(codigoProducto, PageRequest.of(numeroPagina, 10)));
    }

    // Buscar por id
    @GetMapping("/buscar/{idMovimiento}")
    public ResponseEntity<MovimientoInventario> obtenerPorId(@PathVariable Long idMovimiento) {
        return ResponseEntity.ok(movimientoService.obtenerPorId(idMovimiento));
    }

    // Generar reporte, se usa Post porque debe recibir un objeto MovimientoFiltroDTO
    @PostMapping("/reporte")
    public ResponseEntity<List<MovimientoReporteDTO>> generarReporte (@RequestBody MovimientoFiltroDTO filtro) {
        return ResponseEntity.ok(movimientoService.generarReporte(filtro));
    }

    // ACTUALIZACIONES

    // crear nuevo movimiento
    @PostMapping("/crear")
    public ResponseEntity<MovimientoInventario> guardar(@RequestBody DTOMovimientoRequest movimiento) {
        return ResponseEntity.ok(movimientoService.guardar(movimiento));
    }

}

