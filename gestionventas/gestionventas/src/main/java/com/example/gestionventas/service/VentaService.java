package com.example.gestionventas.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.gestionventas.Repository.DetalleVentaRepository;
import com.example.gestionventas.Repository.DevolucionRepository;
import com.example.gestionventas.Repository.VentaRepository;
import com.example.gestionventas.model.DetalleVenta;
import com.example.gestionventas.model.Devolucion;
import com.example.gestionventas.model.Venta;
import com.example.gestionventas.webclient.ClienteClient;
import com.example.gestionventas.webclient.InventarioClient;


import jakarta.transaction.Transactional;


@Service
@Transactional

public class VentaService {
@Autowired
    private VentaRepository ventaRepository;
    @Autowired
    private DetalleVentaRepository detalleVentaRepository;
    @Autowired
    private DevolucionRepository devolucionRepository;
    @Autowired
    private ClienteClient clienteClient;
    @Autowired
    private InventarioClient inventarioClient;

    public List<Venta> getVentas() {
        return ventaRepository.findAll();
    }

    public Venta registrarVenta(Venta venta, List<DetalleVenta> detalles) {
        // Validar cliente
        Map<String, Object> cliente = clienteClient.getClienteById(venta.getClienteId());
        if (cliente == null || cliente.isEmpty()) {
            throw new RuntimeException("Cliente no encontrado");
        }

        // Validar y actualizar stock de productos
        BigDecimal total = BigDecimal.ZERO;
        for (DetalleVenta detalle : detalles) {
            Map<String, Object> producto = inventarioClient.getProductoById(detalle.getProductoId());
            if (producto == null || producto.isEmpty()) {
                throw new RuntimeException("Producto no encontrado: " + detalle.getProductoId());
            }
            Integer stock = (Integer) producto.get("stock");
            if (stock < detalle.getCantidad()) {
                throw new RuntimeException("Stock insuficiente para el producto: " + detalle.getProductoId());
            }
            BigDecimal precio = new BigDecimal(producto.get("precio").toString());
            detalle.setPrecioUnitario(precio);
            detalle.setSubtotal(precio.multiply(BigDecimal.valueOf(detalle.getCantidad())));
            total = total.add(detalle.getSubtotal());
            inventarioClient.actualizarStock(detalle.getProductoId(), detalle.getCantidad());
        }

        // Guardar venta
        venta.setFecha(LocalDateTime.now());
        venta.setTotal(total);
        Venta savedVenta = ventaRepository.save(venta);

        // Guardar detalles
        for (DetalleVenta detalle : detalles) {
            detalle.setVentaId(savedVenta.getId());
            detalleVentaRepository.save(detalle);
        }

        return savedVenta;
    }

    public Devolucion procesarDevolucion(Devolucion devolucion) {
        // Validar venta sin almacenar en variable
        ventaRepository.findById(devolucion.getVentaId())
                .orElseThrow(() -> new RuntimeException("Venta no encontrada"));

        // Validar producto en los detalles de la venta
        List<DetalleVenta> detalles = detalleVentaRepository.findByVentaId(devolucion.getVentaId());
        boolean productoEncontrado = detalles.stream()
                .anyMatch(d -> d.getProductoId().equals(devolucion.getProductoId()) && d.getCantidad() >= devolucion.getCantidad());
        if (!productoEncontrado) {
            throw new RuntimeException("Producto no encontrado en la venta o cantidad inválida");
        }

        // Restaurar stock
        inventarioClient.actualizarStock(devolucion.getProductoId(), -devolucion.getCantidad());

        // Guardar devolución
        devolucion.setFecha(LocalDateTime.now());
        return devolucionRepository.save(devolucion);
    }

    public String generarFactura(Long ventaId) {
        Venta venta = ventaRepository.findById(ventaId)
                .orElseThrow(() -> new RuntimeException("Venta no encontrada"));
        List<DetalleVenta> detalles = detalleVentaRepository.findByVentaId(ventaId);
        Map<String, Object> cliente = clienteClient.getClienteById(venta.getClienteId());

        // Generar factura (simulada como texto, podría integrarse con un servicio de facturación)
        StringBuilder factura = new StringBuilder();
        factura.append("Factura Electrónica\n");
        factura.append("Cliente: ").append(cliente.get("nombre")).append("\n");
        factura.append("Fecha: ").append(venta.getFecha()).append("\n");
        factura.append("Total: ").append(venta.getTotal()).append("\n");
        factura.append("Detalles:\n");
        for (DetalleVenta detalle : detalles) {
            Map<String, Object> producto = inventarioClient.getProductoById(detalle.getProductoId());
            factura.append("- ").append(producto.get("nombre"))
                    .append(": ").append(detalle.getCantidad())
                    .append(" x ").append(detalle.getPrecioUnitario())
                    .append(" = ").append(detalle.getSubtotal()).append("\n");
        }
        return factura.toString();
    }
}