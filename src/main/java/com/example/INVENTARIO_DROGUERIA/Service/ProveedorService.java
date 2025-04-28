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

    public void eliminar(Long id) {
        proveedorRepository.deleteById(id);
    }
}
