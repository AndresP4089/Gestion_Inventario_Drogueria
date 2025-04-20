package com.example.INVENTARIO_DROGUERIA.Controller;

import com.example.INVENTARIO_DROGUERIA.Exceptions.BadRequestException;
import com.example.INVENTARIO_DROGUERIA.Model.Producto;
import com.example.INVENTARIO_DROGUERIA.Service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    //CONSULTAS
    //Paginar
    @GetMapping("/paginar/{numeroPagina}")
    public ResponseEntity<Page<Producto>> obtenerTodos(@PathVariable int numeroPagina) {

        if(numeroPagina<0){
            throw new BadRequestException("No ingrese números negativos");
        }

        return ResponseEntity.ok(productoService.obtenerTodos(PageRequest.of(numeroPagina, 10)));
    }

    //Listar por codigo
    @GetMapping("/listar/codigo/{codigo}")
    public ResponseEntity<Producto> obtenerPorCodigo(@PathVariable String codigo) {
        Producto producto = productoService.obtenerPorCodigo(codigo);
        return ResponseEntity.ok(producto);
    }

    //Listar por nombre
    @GetMapping("/listar/nombre/{nombre}")
    public List<Producto> obtenerPorNombre(@PathVariable String nombre) {
        return productoService.obtenerPorNombre(nombre);
    }

    //ACTUALIZACIONES
    @PostMapping("/crear")
    public Producto guardar(@RequestBody Producto producto) {
        return productoService.guardar(producto);
    }

    @DeleteMapping("/eliminar/{id}")
    public void eliminar(@PathVariable Long id) {
        productoService.eliminar(id);
    }
}
