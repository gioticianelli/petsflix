//package br.com.alura.petsflix;
//
//import br.com.alura.petsflix.principal.Principal;
//import br.com.alura.petsflix.repository.SerieRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//
//@SpringBootApplication
//public class PetsflixApplicationSemWeb {
//
//    @Autowired
//    private SerieRepository repositorio;
//
//	public static void main(String[] args) {
//
//
//        SpringApplication.run(PetsflixApplicationSemWeb.class, args);
//	}
//
//    @Override
//    public void run(String... args) throws Exception {
//        Principal principal = new Principal(repositorio);
//        principal.exibeMenu();
//
//
//
//    }
//}
