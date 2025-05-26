
package com.gestion.privilegio.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "modulo")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Modulo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idModulo;

    @Column(nullable = false, unique = true)
    private String nombreModulo;

    @OneToMany(mappedBy = "modulo", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Privilegio> privilegios;

}



