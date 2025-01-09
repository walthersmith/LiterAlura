package com.walthersmith.literalura.model;

import jakarta.persistence.*;  

@Entity
@Table(name = "libros")
public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String titulo;

    @ManyToOne
    private Autor autor;

    private String idioma;

    private Integer cantidadDescargas;

    public Libro(LibroDTO libroDTO) {
        this.titulo = libroDTO.titulo();
        this.autor = new Autor(libroDTO.autores().getFirst());
        try {
            this.idioma = libroDTO.idiomas().getFirst();
        } catch (Exception e) {
            this.idioma = "Desconocido";
        }
        this.cantidadDescargas = libroDTO.cantidadDescargas();
    }

    public Libro() {
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

 

    public Integer getCantidadDescargas() {
        return cantidadDescargas;
    }

    public void setCantidadDescargas(Integer cantidadDescargas) {
        this.cantidadDescargas = cantidadDescargas;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }
}
