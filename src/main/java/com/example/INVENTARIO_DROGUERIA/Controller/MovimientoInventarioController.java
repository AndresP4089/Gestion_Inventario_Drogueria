package com.example.INVENTARIO_DROGUERIA.Controller;

import com.example.INVENTARIO_DROGUERIA.Model.MovimientoInventario;
import com.example.INVENTARIO_DROGUERIA.Service.MovimientoInventarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/movimientos")
public class MovimientoInventarioController {

    @Autowired
    private MovimientoInventarioService movimientoService;

    @GetMapping("/listar")
    public List<MovimientoInventario> obtenerTodos() {
        return movimientoService.obtenerTodos();
    }

    @GetMapping("/listar/{id}")
    public Optional<MovimientoInventario> obtenerPorId(@PathVariable Long id) {
        return movimientoService.obtenerPorId(id);
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

