package com.walthersmith.literalura.model;

import java.util.List;

import jakarta.persistence.*;

@Entity
@Table(name = "autores")
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer anoNacimiento;
    private Integer anoMuerte;
    private String nombre;

    @OneToMany(mappedBy = "autor",cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Libro> libros;

    public Autor(AutorDTO autorDTO) {
        this.anoNacimiento = autorDTO.anoNacimiento();
        this.anoMuerte = autorDTO.anoMuerte();
        this.nombre = autorDTO.nombre();
    }

    public Autor() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getAnoNacimiento() {
        return anoNacimiento;
    }

    public void setAnoNacimiento(Integer anoNacimiento) {
        this.anoNacimiento = anoNacimiento;
    }   

    public Integer getAnoMuerte() {
        return anoMuerte;
    }

    public void setAnoMuerte(Integer anoMuerte) {
        this.anoMuerte = anoMuerte;
    }   
    
}
