package com.example.INVENTARIO_DROGUERIA.Controller;

import com.example.INVENTARIO_DROGUERIA.Exceptions.BadRequestException;
import com.example.INVENTARIO_DROGUERIA.Model.Producto;
import com.example.INVENTARIO_DROGUERIA.Service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    //CONSULTAS

    // Paginar todo
    @GetMapping("/paginar/{numeroPagina}")
    public ResponseEntity<Page<Producto>> obtenerTodos(@PathVariable int numeroPagina) {

        if(numeroPagina<0){
            throw new BadRequestException("No ingrese números negativos.");
        }

        return ResponseEntity.ok(productoService.obtenerTodos(PageRequest.of(numeroPagina, 10)));
    }

    // Buscar por codigo
    @GetMapping("/buscar/{codigo}")
    public ResponseEntity<Producto> obtenerPorCodigo(@PathVariable String codigo) {
        Producto producto = productoService.obtenerPorCodigo(codigo);
        return ResponseEntity.ok(producto);
    }

    // Paginar por nombre
    @GetMapping("/paginar/{numeroPagina}/nombre/{nombre}")
    public ResponseEntity<Page<Producto>> obtenerPorNombre(@PathVariable int numeroPagina, @PathVariable String nombre) {
        if(numeroPagina<0){
            throw new BadRequestException("No ingrese números negativos.");
        }

        return ResponseEntity.ok(productoService.obtenerPorNombre(nombre, PageRequest.of(numeroPagina, 10)));
    }

    //ACTUALIZACIONES

    // Crear nuevo
    @PostMapping("/crear")
    public ResponseEntity<Producto> guardar(@RequestBody Producto producto) {
        return ResponseEntity.ok(productoService.crearNuevoProducto(producto));
    }

    // Editar existente
    @PutMapping("/editar/{idProducto}")
    public ResponseEntity<Producto> editar(@RequestBody Producto producto, @PathVariable Long idProducto) {
        return ResponseEntity.ok(productoService.editarProducto(producto, idProducto));
    }

    // Inactivar producto(Borrado logico)
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<String> elimnar(@PathVariable Long id) {
        return ResponseEntity.ok("Producto id {" + id + "} ahora está INACTIVO. " + productoService.eliminarProducto(id));
    }
}
