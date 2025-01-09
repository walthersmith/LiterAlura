package com.walthersmith.literalura.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ConvierteDatos implements IConvierteDatos{
    ObjectMapper objectMapper = new ObjectMapper();
    @Override
    public <T> T obtenerDatosConvertidos(String json, Class<T> clase) {
       try{
           return objectMapper.readValue(json,clase);
       }catch (JsonProcessingException e){
           throw new RuntimeException(e);
       }
    }
}
