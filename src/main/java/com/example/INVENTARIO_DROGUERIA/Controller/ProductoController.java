package com.example.INVENTARIO_DROGUERIA.Controller;

import com.example.INVENTARIO_DROGUERIA.Model.Producto;
import com.example.INVENTARIO_DROGUERIA.Service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @GetMapping("/listar")
    public List<Producto> obtenerTodos() {
        return productoService.obtenerTodos();
    }

    @GetMapping("/listar/{id}")
    public Optional<Producto> obtenerPorId(@PathVariable Long id) {
        return productoService.obtenerPorId(id);
    }

    @PostMapping("/crear")
    public Producto guardar(@RequestBody Producto producto) {
        return productoService.guardar(producto);
    }

    @DeleteMapping("/eliminar/{id}")
    public void eliminar(@PathVariable Long id) {
        productoService.eliminar(id);
    }
}
