package com.walthersmith.literalura.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.walthersmith.literalura.model.Autor;

@Repository
public interface AutorRepository extends JpaRepository<Autor,Long> {

    Optional<Autor> findByNombre(String nombre);

    @Query("SELECT a FROM Autor a WHERE a.anoMuerte >= :fecha AND a.anoNacimiento <= :fecha")
    List<Autor> buscarAutoresVivos(int fecha);



    
}
