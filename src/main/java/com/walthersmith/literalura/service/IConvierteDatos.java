package com.walthersmith.literalura.service;

public interface IConvierteDatos {
    <T> T obtenerDatosConvertidos(String json, Class<T> clase);
}
