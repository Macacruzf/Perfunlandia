package com.example.gestionproductos.service;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.gestionproductos.model.Categoria;
import com.example.gestionproductos.repository.CategoriaRepository;


@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    // Crear categoría con manejo de error usando ResponseStatusException
    public Categoria crearCategoria(Categoria categoria) {
        if (categoriaRepository.findByNombre(categoria.getNombre()) != null) {
            throw new ResponseStatusException(
                HttpStatus.CONFLICT, // Código HTTP 409 Conflict
                "La categoría '" + categoria.getNombre() + "' ya existe"
            );
        }
        return categoriaRepository.save(categoria);
    }

    // Resto de métodos sin cambios
    public List<Categoria> listarCategorias() {
        return categoriaRepository.findAll();
    }

    public boolean existeCategoria(Long id) {
        return categoriaRepository.existsById(id);
    }

    public Categoria buscarPorNombre(String nombre) {
        return categoriaRepository.findByNombre(nombre);

    }

    public void eliminarCategoria(Long id) {
        Categoria categoria = categoriaRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Categoría no encontrada"));
        
        if (!categoria.getProductos().isEmpty()) {
            throw new ResponseStatusException(
                HttpStatus.CONFLICT,
                "No se puede eliminar: categoría tiene productos asociados"
            );
        }
        categoriaRepository.deleteById(id);
    }
    

    

}
