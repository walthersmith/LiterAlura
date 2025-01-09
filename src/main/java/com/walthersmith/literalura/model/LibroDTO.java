package com.walthersmith.literalura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record LibroDTO(
   @JsonAlias("title") String titulo,
   @JsonAlias("authors") List<AutorDTO> autores,
   @JsonAlias("languages") List<String> idiomas,
   @JsonAlias("download_count") Integer cantidadDescargas

) {
}
