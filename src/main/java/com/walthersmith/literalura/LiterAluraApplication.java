package com.walthersmith.literalura;

import com.walthersmith.literalura.principal.Principal;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.beans.factory.annotation.Autowired;
import com.walthersmith.literalura.repository.AutorRepository;
import com.walthersmith.literalura.repository.LibroRepository;

@SpringBootApplication
public class LiterAluraApplication  implements CommandLineRunner {

	@Autowired
	private AutorRepository autorRepository;

	@Autowired
	private LibroRepository libroRepository;

	public static void main(String[] args) {
		SpringApplication.run(LiterAluraApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Principal principal = new Principal(autorRepository,libroRepository);
		principal.MenuPrincipal();

	}
}
