package com.example.INVENTARIO_DROGUERIA.Service;

import com.example.INVENTARIO_DROGUERIA.Exceptions.BadRequestException;
import com.example.INVENTARIO_DROGUERIA.Exceptions.NoContentData;
import com.example.INVENTARIO_DROGUERIA.Exceptions.NotFoundDataException;
import com.example.INVENTARIO_DROGUERIA.DTO.Lote.DTOLoteRequest;
import com.example.INVENTARIO_DROGUERIA.Model.Lote;
import com.example.INVENTARIO_DROGUERIA.Model.Producto;
import com.example.INVENTARIO_DROGUERIA.Model.Proveedor;
import com.example.INVENTARIO_DROGUERIA.Repository.LoteRepository;
import com.example.INVENTARIO_DROGUERIA.Repository.ProductoRepository;
import com.example.INVENTARIO_DROGUERIA.Repository.ProveedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class LoteService {

    @Autowired
    private LoteRepository loteRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private ProveedorRepository proveedorRepository;

    // CONSULTAS

    // Paginar todos en orden en desc por fecha ingreso
    public Page<Lote> obtenerPorFechaIngreso(Pageable pageable) {
        Page<Lote> lotes = loteRepository.findAllOrderByFechaIngresoAsc(pageable);

        if(!lotes.hasContent()) {
            throw new NoContentData("No hay contenido.");
        }

        return lotes;
    }

    // Paginar todos en orden asc por fecha vencimiento
    public Page<Lote> obtenerPorFechaVencimiento(Pageable pageable) {
        Page<Lote> lotes = loteRepository.findAllOrderByFechaVencimientoDesc(pageable);

        if(!lotes.hasContent()) {
            throw new NoContentData("No hay contenido.");
        }

        return lotes;
    }

    // Buscar por numero_lote
    public Lote obtenerPorNumeroLote(String numLote) {
        return loteRepository.findByNumeroLote(numLote).
                orElseThrow(() -> new NotFoundDataException("No se encontró un lote con el numero {"+ numLote + "}."));
    }

    // ACTUALIZACIONES

    // Crear lotes
    public Lote crearNuevoLote(DTOLoteRequest request) {
        // Validaciones de campo 'producto'
        if (request.getCodigoProducto() == null) {
            throw new BadRequestException("El lote debe tener un producto asociado.");
        }

        // Validar que exista el producto
        Producto producto = productoRepository.findByCodigo(request.getCodigoProducto())
                .orElseThrow(() -> new BadRequestException("El producto que intenta asociar no existe."));

        // Validación del campo 'proveedor'
        if (request.getNitProveedor() == null) {
            throw new BadRequestException("El lote debe tener un proveedor asociado.");
        }

        // Validar que exista el proveedor
        Proveedor proveedor = proveedorRepository.findByNIT(request.getNitProveedor())
                .orElseThrow(() -> new BadRequestException("El proveedor que intenta asociar no existe."));

        // Validacion del campo 'numeroLote"
        if (request.getNumeroLote() == null || request.getNumeroLote().trim().isEmpty()) {
            throw new BadRequestException("El número de lote no puede estar vacío.");
        }

        // Validación para comprobar si ya existe un lote con el mismo numeroLote
        if (loteRepository.existsByNumeroLote(request.getNumeroLote())) {
            throw new BadRequestException("Ya existe un lote con ese numero de lote.");
        }

        // Validacion para comprobar que tiene fecha de vencimiento y no es igual o menor a la fecha actual
        if (request.getFechaVencimiento() == null || !request.getFechaVencimiento().isAfter(LocalDate.now())) {
            throw new BadRequestException("La fecha de vencimiento del lote debe ser posterior a la fecha actual.");
        }
        
        // Crear nuevo lote, con los datos de request
        
        Lote lote = new Lote();

        // Establecer el producto
        lote.setProducto(producto);

        // Establecer el proveedor
        lote.setProveedor(proveedor);

        // Establecer el numero de lote
        lote.setNumeroLote(request.getNumeroLote());

        // Establece la fecha de ingreso con la fecha actual
        lote.setFechaIngreso(LocalDate.now());

        // Establece la fecha de vencimiento
        lote.setFechaVencimiento(request.getFechaVencimiento());

        // Establece el estado del lote como 'ACTIVO'
        lote.setEstado(Lote.EstadoLote.ACTIVO);

        return loteRepository.save(lote);
    }

    // Editar
    public Lote editarExistente(DTOLoteRequest request, Long idLote) {
        // Validar que existe
        Lote lote = loteRepository.findById(idLote)
                .orElseThrow(() -> new BadRequestException("El lote que intenta editar no existe."));

        // Validar que el lote está activo
        if (lote.getEstado() == Lote.EstadoLote.INACTIVO) {
            throw new BadRequestException("Este lote está inactivo, no se puede editar.");
        }

        // Validar que tenga la fecha de vencimiento
        if (request.getFechaVencimiento() == null) {
            throw new BadRequestException("Debe proporcionar una nueva fecha de vencimiento.");
        }

        // Validar que la fecha de vencimiento no sea igual o anterior a la fecha actual
        if (!request.getFechaVencimiento().isAfter(lote.getFechaIngreso())) {
            throw new BadRequestException("La fecha de vencimiento del lote debe ser posterior a la fecha de ingreso.");
        }

        lote.setId(idLote);
        lote.setFechaVencimiento(request.getFechaVencimiento());

        return loteRepository.save(lote);
    }

    public String eliminarLote(Long idLote) {
        // Validar que exista el lote
        Lote lote = loteRepository.findById(idLote)
                .orElseThrow(() -> new BadRequestException("El lote con id {" + idLote + "} no existe."));

        // Validar que el lote esté activo
        if (lote.getEstado() == Lote.EstadoLote.INACTIVO) {
            throw new BadRequestException("Este lote ya está inactivo.");
        }

        // Al eliminar un lote no se debe eliminar el producto ni el proveedor asociado
        // No eliminar lotes que tengan movimientos asociados
        if (!lote.getMovimientos().isEmpty()) {
            throw new BadRequestException("Este lote está asociado a uno o varios movimientos de inventario, no se puede borrar.");
        }

        // Cambiar estado a inactivo (borrado lógico)
        lote.setEstado(Lote.EstadoLote.INACTIVO);
        loteRepository.save(lote);

        return "Lote eliminado correctamente.";
    }
}

