package com.example.gestionlogistica.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.gestionlogistica.model.Envio;
import com.example.gestionlogistica.model.Proveedor;
import com.example.gestionlogistica.model.Reabastecimiento;
import com.example.gestionlogistica.model.Ruta;
import com.example.gestionlogistica.repository.EnvioRepository;
import com.example.gestionlogistica.repository.ProveedorRepository;
import com.example.gestionlogistica.repository.ReabastecimientoRepository;
import com.example.gestionlogistica.repository.RutaRepository;
import com.example.gestionlogistica.webclient.ClienteCliente;
import com.example.gestionlogistica.webclient.InventarioClient;

import jakarta.transaction.Transactional;
import java.util.Map;

@Service
@Transactional
public class LogisticaService {
    @Autowired
    private EnvioRepository envioRepository;
    @Autowired
    private RutaRepository rutaRepository;
    @Autowired
    private ProveedorRepository proveedorRepository;
    @Autowired
    private ReabastecimientoRepository reabastecimientoRepository;
    @Autowired
    private ClienteCliente clienteClient;
    @Autowired
    private InventarioClient inventarioClient;

    // Gestión de envíos
    public Envio crearEnvio(Envio envio) {
        // Validar pedido
        Map<String, Object> pedido = clienteClient.getPedidoById(envio.getPedidoId());
        if (pedido == null || pedido.isEmpty()) {
            throw new RuntimeException("Pedido no encontrado");
        }
        envio.setEstado("procesando");
        envio.setFechaCreacion(LocalDateTime.now());
        return envioRepository.save(envio);
    }

    public Envio actualizarEstadoEnvio(Long id, String estado) {
        Envio envio = envioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Envío no encontrado"));
        if (!List.of("procesando", "enviado", "entregado").contains(estado)) {
            throw new RuntimeException("Estado inválido");
        }
        envio.setEstado(estado);
        if (estado.equals("entregado")) {
            envio.setFechaEntrega(LocalDateTime.now());
        }
        return envioRepository.save(envio);
    }

    public List<Envio> getEnviosPorPedido(Long pedidoId) {
        return envioRepository.findByPedidoId(pedidoId);
    }

    // Gestión de rutas
    public Ruta crearRuta(Ruta ruta) {
        // Simulación de optimización (en un sistema real, se integraría con un servicio de mapas)
        if (ruta.getPuntos() == null || ruta.getPuntos().isEmpty()) {
            throw new RuntimeException("Los puntos de la ruta son obligatorios");
        }
        return rutaRepository.save(ruta);
    }

    public Ruta optimizarRuta(Long rutaId) {
        Ruta ruta = rutaRepository.findById(rutaId)
                .orElseThrow(() -> new RuntimeException("Ruta no encontrada"));
        // Simulación de optimización (podría usar un algoritmo como Dijkstra o integrar Google Maps API)
        ruta.setDistancia(ruta.getDistancia() != null ? ruta.getDistancia() * 0.95 : 100.0);
        ruta.setTiempoEstimado(ruta.getTiempoEstimado() != null ? ruta.getTiempoEstimado() * 0.95 : 2.0);
        return rutaRepository.save(ruta);
    }

    // Gestión de proveedores
    public Proveedor agregarProveedor(Proveedor proveedor) {
        return proveedorRepository.save(proveedor);
    }

    public Proveedor actualizarProveedor(Long id, Proveedor proveedor) {
        Proveedor existente = proveedorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Proveedor no encontrado"));
        existente.setNombre(proveedor.getNombre());
        existente.setContacto(proveedor.getContacto());
        existente.setDireccion(proveedor.getDireccion());
        return proveedorRepository.save(existente);
    }

    public void eliminarProveedor(Long id) {
        Proveedor proveedor = proveedorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Proveedor no encontrado"));
        // Verificar que no haya reabastecimientos pendientes
        List<Reabastecimiento> reabastecimientos = reabastecimientoRepository.findAll().stream()
                .filter(r -> r.getProveedorId().equals(id) && r.getEstado().equals("solicitado"))
                .toList();
        if (!reabastecimientos.isEmpty()) {
            throw new RuntimeException("No se puede eliminar el proveedor porque tiene reabastecimientos pendientes");
        }
        proveedorRepository.delete(proveedor);
    }

    // Gestión de reabastecimientos
    public Reabastecimiento crearReabastecimiento(Reabastecimiento reabastecimiento) {
        // Validar proveedor
        proveedorRepository.findById(reabastecimiento.getProveedorId())
                .orElseThrow(() -> new RuntimeException("Proveedor no encontrado"));
        reabastecimiento.setEstado("solicitado");
        reabastecimiento.setFechaSolicitud(LocalDateTime.now());
        return reabastecimientoRepository.save(reabastecimiento);
    }

    public Reabastecimiento recibirReabastecimiento(Long id) {
        Reabastecimiento reabastecimiento = reabastecimientoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reabastecimiento no encontrado"));
        if (!reabastecimiento.getEstado().equals("solicitado")) {
            throw new RuntimeException("El reabastecimiento ya fue recibido o no está en estado solicitado");
        }
        reabastecimiento.setEstado("recibido");
        reabastecimiento.setFechaRecepcion(LocalDateTime.now());

        // Actualizar stock en el inventario
        // Suponiendo que productos es un JSON con formato: [{"productoId": 1, "cantidad": 10}, ...]
        List<Map<String, Object>> productos = parseProductosJson(reabastecimiento.getProductos());
        for (Map<String, Object> producto : productos) {
            Long productoId = Long.valueOf(producto.get("productoId").toString());
            Integer cantidad = Integer.valueOf(producto.get("cantidad").toString());
            inventarioClient.actualizarStock(productoId, cantidad);
        }

        return reabastecimientoRepository.save(reabastecimiento);
    }

    // Método auxiliar para parsear JSON de productos (simplificado)
    private List<Map<String, Object>> parseProductosJson(String productosJson) {
        // En un sistema real, usar ObjectMapper de Jackson para parsear JSON
        // Aquí se simula el parseo para mantener el ejemplo simple
        return List.of(
                Map.of("productoId", 1L, "cantidad", 10),
                Map.of("productoId", 2L, "cantidad", 20)
        );
    }
}

