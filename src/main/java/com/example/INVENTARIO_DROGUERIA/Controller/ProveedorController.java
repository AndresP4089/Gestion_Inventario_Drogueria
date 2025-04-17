package com.example.INVENTARIO_DROGUERIA.Controller;

import com.example.INVENTARIO_DROGUERIA.Model.Proveedor;
import com.example.INVENTARIO_DROGUERIA.Service.ProveedorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/proveedores")
public class ProveedorController {

    @Autowired
    private ProveedorService proveedorService;

    @GetMapping("/listar")
    public List<Proveedor> obtenerTodos() {
        return proveedorService.obtenerTodos();
    }

    @GetMapping("/listar/{id}")
    public Optional<Proveedor> obtenerPorId(@PathVariable Long id) {
        return proveedorService.obtenerPorId(id);
    }

    @PostMapping("/crear")
    public Proveedor guardar(@RequestBody Proveedor proveedor) {
        return proveedorService.guardar(proveedor);
    }

    @DeleteMapping("/eliminar/{id}")
    public void eliminar(@PathVariable Long id) {
        proveedorService.eliminar(id);
    }
}
