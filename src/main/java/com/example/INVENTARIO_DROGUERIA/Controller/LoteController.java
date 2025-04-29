package com.example.INVENTARIO_DROGUERIA.Controller;

import com.example.INVENTARIO_DROGUERIA.Exceptions.BadRequestException;
import com.example.INVENTARIO_DROGUERIA.Model.Lote;
import com.example.INVENTARIO_DROGUERIA.Service.LoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/lotes")
public class LoteController {

    @Autowired
    private LoteService loteService;

    // CONSULTAS

    // Paginar todo
    @GetMapping("/paginar/{numeroPagina}")
    public ResponseEntity<Page<Lote>> obtenerTodos(@PathVariable int numeroPagina) {

        if (numeroPagina<0) {
            throw new BadRequestException("No ingrese números negativos.");
        }

        return ResponseEntity.ok(loteService.obtenerTodos(PageRequest.of(numeroPagina, 10)));
    }

    @GetMapping("/listar/{id}")
    public Optional<Lote> obtenerPorId(@PathVariable Long id) {
        return loteService.obtenerPorId(id);
    }

    @PostMapping("/crear")
    public Lote guardar(@RequestBody Lote lote) {
        return loteService.guardar(lote);
    }

    @DeleteMapping("/eliminar/{id}")
    public void eliminar(@PathVariable Long id) {
        loteService.eliminar(id);
    }
}

