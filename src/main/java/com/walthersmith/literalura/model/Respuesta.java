package com.walthersmith.literalura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)public record Respuesta(
       @JsonAlias("results") LibroDTO[] libros
) {
}
