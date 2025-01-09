package com.walthersmith.literalura.principal;

import com.walthersmith.literalura.model.Autor;
import com.walthersmith.literalura.model.Libro;
import com.walthersmith.literalura.model.LibroDTO;
import com.walthersmith.literalura.model.Respuesta;
import com.walthersmith.literalura.repository.AutorRepository;
import com.walthersmith.literalura.repository.LibroRepository;
import com.walthersmith.literalura.service.ConsumoAPI;
import com.walthersmith.literalura.service.ConvierteDatos;
 
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
 
import org.springframework.beans.factory.annotation.Autowired;


public class Principal {
    private Scanner teclado = new Scanner(System.in);
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private final String URL_BASE = "https://gutendex.com";
    private ConvierteDatos convierteDatos = new ConvierteDatos();
  
    private LibroRepository libroRepository;
 
    private AutorRepository autorRepository;


    public Principal(AutorRepository autorRepository, LibroRepository libroRepository) {
      this.autorRepository = autorRepository;
      this.libroRepository = libroRepository;
    }



    public void  MenuPrincipal(){
        var opcion = -1;
        while (opcion != 0) {
            System.out.println("-----------------------------------------------");
            System.out.println(" ▌║█║▌│║▌│║▌║▌█║ LiterAlura ▌│║▌║▌│║║▌█║▌║█");
            System.out.println("-----------------------------------------------");
            var menu = """
                    1 - Buscar Libro por titulo
                    2 - Listar libros registrados
                    3 - Listar autores registrados
                    4 - Listar autores vivos de un determinado año
                    5 - listar libros por idioma
                    0 - Salir
                    """;
            System.out.println(menu);
            opcion = teclado.nextInt();
            teclado.nextLine();

            switch (opcion) {
                case 1:
                    buscarLibro();
                    break;
                case 2:
                    listarLibros();
                    break;
                case 3:
                    listarAutores();
                    break;
                case 4:
                    System.out.println("Ingrese el año de busqueda: ");
                    Integer ano = teclado.nextInt();
                    teclado.nextLine();
                    listarAutoresVivos(ano);
                    break;
                case 5:
                    System.out.println("Ingrese el idioma de busqueda: ");
                    String idioma = teclado.nextLine();
                    listarLibrosPorIdioma(idioma);
                    break;
                case 0:
                    System.out.println("Cerrando la aplicación...");
                    break;
                default:
                    System.out.println("Opción inválida");
            }
        }
    }



    //Metodos API
    private  LibroDTO getDatosLibro(String busqueda){
        var url = URL_BASE+"/books/?search="+busqueda.replace(" ","%20");
        System.out.println(url);
        var json  = consumoAPI.obtenerDatos(url);
//        System.out.println("RESPUESTA:"+json);
//        List<LibroDTO> resultados = new ArrayList<>();
        Respuesta respuesta =  convierteDatos.obtenerDatosConvertidos(json, Respuesta.class);
        if(respuesta.libros().length == 0){
            System.out.println("No se encontraron resultados");
            return null;
        }

        LibroDTO resultados = Arrays.stream(respuesta.libros()).toList().getFirst();
        return resultados;
    }



    //Metodos Menu
    private void buscarLibro() {
        String nombreLibro;
        while(true) {
            System.out.println("> Ingresa el nombre del libro que deseas buscar: ");
            nombreLibro = teclado.nextLine();
            if (nombreLibro.isEmpty()) {
                System.out.println("Por favor Ingrese un termino de busqueda");
                continue;
            }
            break;
        }

        System.out.println("RESPUESTA CONVERTIDA: " );
        LibroDTO libroDTO =  getDatosLibro(nombreLibro);
        if(libroDTO == null){
            return;
        }
        System.out.println(libroDTO); 
        guardarLibro(libroDTO); 
    }

    private void guardarLibro(LibroDTO libroDTO){
        Libro libro = new Libro(libroDTO);
        //valida si el libro ya existe
        Optional<Libro> libroExistente = libroRepository.findByTitulo(libroDTO.titulo());
        if(libroExistente.isPresent()){
            System.out.println("El libro ya existe");
            return;
        }



        Optional<Autor> autordb  = autorRepository.findByNombre(libroDTO.autores().getFirst().nombre());
        if(autordb.isPresent()){
            libro.setAutor(autordb.get());
        }else{
            Autor autor = new Autor(libroDTO.autores().getFirst());
            System.out.println("Autor: "+autor.getNombre());
            autorRepository.save(autor);
            libro.setAutor(autor);
        }

        libroRepository.save(libro);
        System.out.println("Libro guardado con éxito");
    }

    //listar libros registrados
    private void listarLibros(){
        List<Libro> libros = libroRepository.findAll();
        System.out.println("Libros registrados: "+libros.size());
        for(Libro libro : libros){
            System.out.println("Titulo: "+libro.getTitulo());
            System.out.println("Autor: "+libro.getAutor().getNombre());
            System.out.println("Año de nacimiento: "+libro.getAutor().getAnoNacimiento());
            System.out.println("Año de muerte: "+libro.getAutor().getAnoMuerte());
            System.out.println("Cantidad de descargas: "+libro.getCantidadDescargas());
            System.out.println("-----------------------------------------------");
        }
    }

    private void listarAutores(){
        List<Autor> autores = autorRepository.findAll();
        System.out.println("Autores registrados: "+autores.size());
        for(Autor autor : autores){
            System.out.println("Nombre: "+autor.getNombre());
            System.out.println("Año de nacimiento: "+autor.getAnoNacimiento());
            System.out.println("Año de muerte: "+autor.getAnoMuerte());
            System.out.println("-----------------------------------------------");
        }
    }

    private void listarAutoresVivos(Integer ano){
        List<Autor> autores = autorRepository.buscarAutoresVivos(ano);
        System.out.println("Autores vivos: "+autores.size());
        for(Autor autor : autores){
            System.out.println("Nombre: "+autor.getNombre());
            System.out.println("Año de nacimiento: "+autor.getAnoNacimiento());
            System.out.println("Año de muerte: "+autor.getAnoMuerte());
            System.out.println("-----------------------------------------------");
        }
        
    }

    private void listarLibrosPorIdioma(String idioma){
        List<Libro> libros = libroRepository.findByIdioma(idioma).stream().toList();
        System.out.println("Libros por idioma: "+libros.size());
        for(Libro libro : libros){
            System.out.println("Titulo: "+libro.getTitulo());
            System.out.println("Autor: "+libro.getAutor().getNombre());
            System.out.println("Año de nacimiento: "+libro.getAutor().getAnoNacimiento());
            System.out.println("Año de muerte: "+libro.getAutor().getAnoMuerte());
            System.out.println("Cantidad de descargas: "+libro.getCantidadDescargas());
            System.out.println("Idioma: "+libro.getIdioma());   
            System.out.println("-----------------------------------------------");
        }
    }



}



