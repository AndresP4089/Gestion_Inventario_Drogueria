package com.example.INVENTARIO_DROGUERIA.Controller;

import com.example.INVENTARIO_DROGUERIA.Exceptions.BadRequestException;
import com.example.INVENTARIO_DROGUERIA.Model.DTOLoteRequest;
import com.example.INVENTARIO_DROGUERIA.Model.Lote;
import com.example.INVENTARIO_DROGUERIA.Service.LoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/lotes")
public class LoteController {

    @Autowired
    private LoteService loteService;

    // CONSULTAS

    // Paginar todo desc fecha ingreso
    @GetMapping("/paginar/fecha-ingreso/{numeroPagina}")
    public ResponseEntity<Page<Lote>> obtenerTodosFechaIngreso(@PathVariable int numeroPagina) {

        if (numeroPagina<0) {
            throw new BadRequestException("No ingrese números negativos.");
        }

        return ResponseEntity.ok(loteService.obtenerPorFechaIngreso(PageRequest.of(numeroPagina, 10)));
    }

    // Paginar todo asc fecha vencimiento
    @GetMapping("/paginar/fecha-vencimiento/{numeroPagina}")
    public ResponseEntity<Page<Lote>> obtenerTodosFechaVencimiento(@PathVariable int numeroPagina) {

        if (numeroPagina<0) {
            throw new BadRequestException("No ingrese números negativos.");
        }

        return ResponseEntity.ok(loteService.obtenerPorFechaVencimiento(PageRequest.of(numeroPagina, 10)));
    }

    // Buscar por NIT
    @GetMapping("/buscar/{numLote}")
    public ResponseEntity<Lote> obtenerPorNumeroLote(@PathVariable String numLote) {
        Lote lote = loteService.obtenerPorNumeroLote(numLote);
        return ResponseEntity.ok(lote);
    }

    // ACTUALIZACIONES

    // Crear
    @PostMapping("/crear")
    public ResponseEntity<Lote> guardar(@RequestBody DTOLoteRequest request){
        return ResponseEntity.ok(loteService.crearNuevoLote(request));
    }

    @GetMapping("/listar/{id}")
    public Optional<Lote> obtenerPorId(@PathVariable Long id) {
        return loteService.obtenerPorId(id);
    }

    @DeleteMapping("/eliminar/{id}")
    public void eliminar(@PathVariable Long id) {
        loteService.eliminar(id);
    }
}

