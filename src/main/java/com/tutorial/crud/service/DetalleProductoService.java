package com.tutorial.crud.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.tutorial.crud.entity.DetalleProducto;
import com.tutorial.crud.security.repository.DetalleProductoRepository;

@Service
@Transactional
public class DetalleProductoService {

    @Autowired
    private DetalleProductoRepository detalleProductoRepository;

    // Lista todos los detalles de productos
    public List<DetalleProducto> findAll() {
        return detalleProductoRepository.findAll();
    }

    // Obtiene un detalle de producto por su ID
    public Optional<DetalleProducto> findById(Long idDetalleProducto) {
        return detalleProductoRepository.findById(idDetalleProducto);
    }

    // Guarda un detalle de producto
    public void save(DetalleProducto detalleProducto) {
        detalleProductoRepository.save(detalleProducto);
    }

    // Elimina un detalle de producto por su ID
    public void deleteById(Long idDetalleProducto) {
        detalleProductoRepository.deleteById(idDetalleProducto);
    }

    // Verifica si un detalle de producto existe por su ID
    public boolean existsById(Long idDetalleProducto) {
        return detalleProductoRepository.existsById(idDetalleProducto);
    }
}
