package br.com.alura.petsflix;

import br.com.alura.petsflix.principal.Principal;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PetsflixApplication implements CommandLineRunner {

	public static void main(String[] args) {

        SpringApplication.run(PetsflixApplication.class, args);
	}

    @Override
    public void run(String... args) throws Exception {
        Principal principal = new Principal();
        principal.exibeMenu();



    }
}
