package com.walthersmith.literalura.principal;

import com.walthersmith.literalura.service.ConsumoAPI;
import com.walthersmith.literalura.service.ConvierteDatos;

import java.util.Scanner;

public class Principal {
    private Scanner teclado = new Scanner(System.in);
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private final String URL_BASE = "https://gutendex.com/";
    private ConvierteDatos convierteDatos = new ConvierteDatos();

}
