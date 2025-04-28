package com.example.INVENTARIO_DROGUERIA.Service;

import com.example.INVENTARIO_DROGUERIA.Exceptions.BadRequestException;
import com.example.INVENTARIO_DROGUERIA.Exceptions.NoContentData;
import com.example.INVENTARIO_DROGUERIA.Exceptions.NotFoundDataException;
import com.example.INVENTARIO_DROGUERIA.Model.Proveedor;
import com.example.INVENTARIO_DROGUERIA.Repository.ProveedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class ProveedorService {

    @Autowired
    private ProveedorRepository proveedorRepository;

    // CONSULTAS

    // Paginar todos en orden alfabetico
    public Page<Proveedor> obtenerTodos(Pageable pageable) {
        Page<Proveedor> proveedores = proveedorRepository.findAllOrderByAsc(pageable);

        if(!proveedores.hasContent()) {
            throw new NoContentData("No hay contenido.");
        }

        return proveedores;
    }

    // Paginar por NIT en orden alfabetico
    public Proveedor obtenerPorNIT(String NIT) {
        return proveedorRepository.findByNIT(NIT).
                orElseThrow(() -> new NotFoundDataException("No se encontró un proveedor con el NIT {"+ NIT + "}."));
    }

    // Paginar por nombre en orden alfabetico
    public Page<Proveedor> obtenerPorNombre(String nombre, Pageable pageable) {
        nombre = nombre.trim();
        Page<Proveedor> proveedores = proveedorRepository.findByNombre(nombre, pageable);

        // Validar que existan proveedores por ese nombre
        if(!proveedores.hasContent()) {
            throw new NoContentData("No hay contenido.");
        }

        return proveedores;
    }

    // ACTUALIZACIONES

    // Crear un nuevo proveedor
    public Proveedor crearNuevoProveedor(Proveedor proveedor) {
        // Validaciones de campo 'nombre'
        if (proveedor.getNombre() == null || proveedor.getNombre().isBlank()) {
            throw new BadRequestException("El proveedor debe tener nombre.");
        }

        // Validación del campo 'NIT'
        if (proveedor.getNit() == null || proveedor.getNit().isBlank()) {
            throw new BadRequestException("El proveedor debe tener un NIT.");
        }

        // Validación para comprobar si ya existe un proveedor con el mismo NIT
        if (proveedorRepository.existsByNit(proveedor.getNit())) {
            throw new BadRequestException("Ya existe un proveedor con ese NIT.");
        }

        // Validación para comprobar si ya existe un proveedor con el mismo nombre
        if (proveedorRepository.existsByNombre(proveedor.getNombre())) {
            throw new BadRequestException("Ya existe un proveedor con ese nombre.");
        }

        // Validación de telefono
        if (proveedor.getTelefono() == null || proveedor.getTelefono().isBlank()) {
            throw new BadRequestException("El proveedor debe tener un telefono.");
        }

        // Validación de email
        if (proveedor.getEmail() == null || proveedor.getEmail().isBlank()) {
            throw new BadRequestException("El proveedor debe tener un correo electrónico.");
        }

        // Establece el estado del proveedor como 'ACTIVO'
        proveedor.setEstado(Proveedor.EstadoProveedor.ACTIVO);

        return proveedorRepository.save(proveedor);
    }

    // Editar un proveedor existente
    public Proveedor editarProveedor(Proveedor proveedorNuevo, Long id) {

        // Validar que exista el proveedor
        Proveedor proveedorActual = proveedorRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("El proveedor con id {" + id + "} no existe."));

        // Validar que el proveedor esté activo, no editar proveedores inactivos
        if (proveedorActual.getEstado() == Proveedor.EstadoProveedor.INACTIVO) {
            throw new BadRequestException("Este proveedor está inactivo, no se puede editar.");
        }

        // Validar que no exista otro proveedor con el mismo nombre (y diferente id)
        Optional<Proveedor> proveedorConMismoNombre = proveedorRepository.findByNombre(proveedorNuevo.getNombre());
        if (proveedorConMismoNombre.isPresent() && !proveedorConMismoNombre.get().getId().equals(id)) {
            throw new BadRequestException("Ya existe otro proveedor con ese nombre.");
        }

        // Validar que no exista otro proveedor con el mismo nit (y diferente id)
        if (proveedorRepository.existsByNitAndIdNot(proveedorNuevo.getNit(), id)) {
            throw new BadRequestException("Ya existe otro proveedor con ese NIT.");
        }

        // Aqui se pueden agregar mas validaciones en los datos direccion, telefono y correo

        // Solo actualiza campos no nulos o no vacíos(Actualizacion parcial)
        if (proveedorNuevo.getNombre() != null && !proveedorNuevo.getNombre().isBlank()) {
            proveedorActual.setNombre(proveedorNuevo.getNombre());
        }

        if (proveedorNuevo.getNit() != null && !proveedorNuevo.getNit().isBlank()) {
            proveedorActual.setNit(proveedorNuevo.getNit());
        }

        if (proveedorNuevo.getDireccion() != null && !proveedorNuevo.getDireccion().isBlank()) {
            proveedorActual.setDireccion(proveedorNuevo.getDireccion());
        }

        if (proveedorNuevo.getEmail() != null && !proveedorNuevo.getEmail().isBlank()) {
            proveedorActual.setEmail(proveedorNuevo.getEmail());
        }

        proveedorActual.setEstado(Proveedor.EstadoProveedor.ACTIVO);

        return proveedorRepository.save(proveedorActual);
    }

    // Eliminar (Borrado logico)
    @Transactional
    public String eliminarProveedor(Long idProveedor) {
        // Validar que existe el proveedor
        Proveedor proveedorActual = proveedorRepository.findById(idProveedor)
                .orElseThrow(() -> new BadRequestException("El proveedor con id {" + idProveedor + "} no existe."));

        // Validar que el proveedor esté activo, no editar proveedores inactivos
        if (proveedorActual.getEstado() == Proveedor.EstadoProveedor.INACTIVO) {
            throw new BadRequestException("Este proveedor ya está inactivo.");
        }

        // Este método inactiva el proveedor pero no a todos los lotes asociados.
        // Si en el futuro se agregan otras entidades relacionadas, también se deben inactivar aquí.

        proveedorRepository.actualizarEstadoProveedor(idProveedor, Proveedor.EstadoProveedor.INACTIVO.name());

        return "Eliminación exitosa.";

    }
}
