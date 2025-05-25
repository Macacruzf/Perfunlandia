package com.example.gestionventas.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.gestionventas.Repository.DetalleVentaRepository;
import com.example.gestionventas.Repository.VentaRepository;
import com.example.gestionventas.model.DetalleVenta;
import com.example.gestionventas.model.Venta;
import com.example.gestionventas.webclient.ClienteClient;
import com.example.gestionventas.webclient.DireccionClient;
import com.example.gestionventas.webclient.EstadoClient;
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
    private ClienteClient clienteClient;

    @Autowired
    private DireccionClient direccionClient;

    @Autowired
    private EstadoClient estadoClient;

    @Autowired
    private InventarioClient inventarioClient;

    public List<Venta> getVentas() {
        return ventaRepository.findAll();
    }

    public Venta registrarVenta(Venta venta, List<DetalleVenta> detalles) {
        // Validar cliente (microservicio de usuario)
        Map<String, Object> cliente = clienteClient.getClienteById(venta.getIdCliente());
        if (cliente == null || cliente.isEmpty()) {
            throw new RuntimeException("Cliente no encontrado");
        }

        // Validar dirección (microservicio de dirección)
        Map<String, Object> direccion = direccionClient.getDireccionById(venta.getIdDireccion());
        if (direccion == null || direccion.isEmpty()) {
            throw new RuntimeException("Dirección no encontrada");
        }

        // Validar estado_envio (microservicio de estado)
        Map<String, Object> estadoEnvio = estadoClient.getEstadoEnvioById(venta.getIdEstadoEnvio());
        if (estadoEnvio == null || estadoEnvio.isEmpty()) {
            throw new RuntimeException("Estado de envío no encontrado");
        }

        // Validar y actualizar stock de productos (microservicio de producto)
        BigDecimal total = BigDecimal.ZERO;
        for (DetalleVenta detalle : detalles) {
            Map<String, Object> producto = inventarioClient.getProductoById(detalle.getIdProducto());
            if (producto == null || producto.isEmpty()) {
                throw new RuntimeException("Producto no encontrado: " + detalle.getIdProducto());
            }
            Integer stock = (Integer) producto.get("stock");
            if (stock < detalle.getCantidad()) {
                throw new RuntimeException("Stock insuficiente para el producto: " + detalle.getIdProducto());
            }
            BigDecimal precio = new BigDecimal(producto.get("precio").toString());
            detalle.setSubtotal(precio.multiply(BigDecimal.valueOf(detalle.getCantidad())));
            total = total.add(detalle.getSubtotal());
            inventarioClient.actualizarStock(detalle.getIdProducto(), detalle.getCantidad());
        }

        // Guardar venta
        venta.setFechaventa(LocalDateTime.now());
        venta.setTotal(total);
        Venta savedVenta = ventaRepository.save(venta);

        // Guardar detalles
        for (DetalleVenta detalle : detalles) {
            detalle.setIdVenta(savedVenta.getIdVenta());
            detalleVentaRepository.save(detalle);
        }

        return savedVenta;
    }

    public String generarFactura(Long ventaId) {
        Venta venta = ventaRepository.findById(ventaId)
                .orElseThrow(() -> new RuntimeException("Venta no encontrada"));
        List<DetalleVenta> detalles = detalleVentaRepository.findByVentaId(ventaId);
        Map<String, Object> cliente = clienteClient.getClienteById(venta.getIdCliente());
        Map<String, Object> direccion = direccionClient.getDireccionById(venta.getIdDireccion());
        Map<String, Object> estadoEnvio = estadoClient.getEstadoEnvioById(venta.getIdEstadoEnvio());

        // Generar factura (simulada como texto)
        StringBuilder factura = new StringBuilder();
        factura.append("Factura Electrónica\n");
        factura.append("Cliente: ").append(cliente.get("nombre")).append("\n");
        factura.append("Dirección: ").append(direccion.get("direccion")).append("\n");
        factura.append("Estado de Envío: ").append(estadoEnvio.get("estado")).append("\n");
        factura.append("Fecha: ").append(venta.getFechaventa()).append("\n");
        factura.append("Total: ").append(venta.getTotal()).append("\n");
        factura.append("Detalles:\n");
        for (DetalleVenta detalle : detalles) {
            Map<String, Object> producto = inventarioClient.getProductoById(detalle.getIdProducto());
            factura.append("- ").append(producto.get("nombre"))
                    .append(": ").append(detalle.getCantidad())
                    .append(" = ").append(detalle.getSubtotal()).append("\n");
        }
        return factura.toString();
    }
}