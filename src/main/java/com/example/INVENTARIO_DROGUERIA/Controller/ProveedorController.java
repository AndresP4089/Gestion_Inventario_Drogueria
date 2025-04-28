package com.example.INVENTARIO_DROGUERIA.Controller;

import com.example.INVENTARIO_DROGUERIA.Exceptions.BadRequestException;
import com.example.INVENTARIO_DROGUERIA.Model.Proveedor;
import com.example.INVENTARIO_DROGUERIA.Service.ProveedorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/proveedores")
public class ProveedorController {

    @Autowired
    private ProveedorService proveedorService;

    // CONSULTAS

    // Paginar todo
    @GetMapping("/paginar/{numeroPagina}")
    public ResponseEntity<Page<Proveedor>> obtenerTodos(@PathVariable int numeroPagina) {

        if (numeroPagina<0) {
            throw new BadRequestException("No ingrese números negativos.");
        }

        return ResponseEntity.ok(proveedorService.obtenerTodos(PageRequest.of(numeroPagina, 10)));
    }

    // Buscar por NIT
    @GetMapping("/buscar/{nit}")
    public ResponseEntity<Proveedor> obtenerPorNIT(@PathVariable String nit) {
        Proveedor proveedor = proveedorService.obtenerPorNIT(nit);
        return ResponseEntity.ok(proveedor);
    }

    // Paginar por nombre
    @GetMapping("/paginar/{numeroPagina}/nombre/{nombre}")
    public ResponseEntity<Page<Proveedor>> obtenerPorNombre(@PathVariable int numeroPagina, @PathVariable String nombre) {
        if(numeroPagina<0){
            throw new BadRequestException("No ingrese números negativos.");
        }

        return ResponseEntity.ok(proveedorService.obtenerPorNombre(nombre, PageRequest.of(numeroPagina, 10)));
    }

    // ACTUALIZACIONES

    // Crear nuevo
    @PostMapping("/crear")
    public ResponseEntity<Proveedor> guardar(@RequestBody Proveedor proveedor) {
        return ResponseEntity.ok(proveedorService.crearNuevoProveedor(proveedor));
    }

    // Editar existente
    @PutMapping("/editar/{id}")
    public void editar(@PathVariable Long id) {
        proveedorService.eliminar(id);
    }

    // Inactivar proveedor(Borrado logico)
    @DeleteMapping("/eliminar/{id}")
    public void eliminar(@PathVariable Long id) {
        proveedorService.eliminar(id);
    }
}
