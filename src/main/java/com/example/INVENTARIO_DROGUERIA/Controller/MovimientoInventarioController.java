package com.example.INVENTARIO_DROGUERIA.Controller;

import com.example.INVENTARIO_DROGUERIA.Exceptions.BadRequestException;
import com.example.INVENTARIO_DROGUERIA.Model.MovimientoInventario;
import com.example.INVENTARIO_DROGUERIA.Service.MovimientoInventarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/movimientos")
public class MovimientoInventarioController {

    @Autowired
    private MovimientoInventarioService movimientoService;

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

    @GetMapping("/buscar/{idMovimiento}")
    public ResponseEntity<MovimientoInventario> obtenerPorId(@PathVariable Long idMovimiento) {
        return ResponseEntity.ok(movimientoService.obtenerPorId(idMovimiento));
    }

    @PostMapping("/crear")
    public MovimientoInventario guardar(@RequestBody MovimientoInventario movimiento) {
        return movimientoService.guardar(movimiento);
    }

    @DeleteMapping("/eliminar/{id}")
    public void eliminar(@PathVariable Long id) {
        movimientoService.eliminar(id);
    }
}

