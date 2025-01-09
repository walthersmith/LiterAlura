package com.walthersmith.literalura.model;

import jakarta.persistence.*;

@Entity
@Table(name = "idiomas")
public class Idioma {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String descripcion;

    @ManyToOne
    private Libro libro;
}
