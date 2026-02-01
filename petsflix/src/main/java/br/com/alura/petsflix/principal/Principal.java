package br.com.alura.petsflix.principal;

import br.com.alura.petsflix.model.DadosSerie;
import br.com.alura.petsflix.model.DadosTemporada;
import br.com.alura.petsflix.model.Serie;
import br.com.alura.petsflix.repository.SerieRepository;
import br.com.alura.petsflix.service.ConsumoApi;
import br.com.alura.petsflix.service.ConverteDados;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Principal {

    private Scanner leitura = new Scanner(System.in);
    private ConsumoApi consumo = new ConsumoApi();
    private ConverteDados conversor = new ConverteDados();
    private final String ENDERECO = "https://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey=" + System.getenv("OMDB_KEY");
    private List<DadosSerie> dadosSeries = new ArrayList<>();

   private SerieRepository repositorio;

    public Principal(SerieRepository repositorio) {
        this.repositorio = repositorio;
    }


    public void exibeMenu() {
        var opcao = -1;
        while(opcao != 0) {
            var menu = """
                    1 - Buscar séries
                    2 - Buscar episódios
                    3 - Listar séries buscadas
                    4 - Buscar serie pelo ator
                    5 - Listar Atores Buscados
                                    
                    0 - Sair                                 
                    """;

            System.out.println(menu);
            opcao = leitura.nextInt();
            leitura.nextLine();

            switch (opcao) {
                case 1:
                    buscarSerieWeb();
                    break;
                case 2:
                    buscarEpisodioPorSerie();
                    break;
                case 3:
                    listarSeriesBuscadas();
                case 4:
                    buscarSeriesPorAtor();
                    break;
                case 5:
                    listarAtoresBuscados();
                    break;
                case 0:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida");
            }
        }
    }

    private void buscarSerieWeb() {
        DadosSerie dados = getDadosSerie();
        Serie serie = new Serie(dados);
      //  dadosSeries.add(dados);
        repositorio.save(serie);
        System.out.println(dados);
    }

    private DadosSerie getDadosSerie() {
        System.out.println("Digite o nome da série para busca");
        var nomeSerie = leitura.nextLine();
        var json = consumo.obterDados(ENDERECO + nomeSerie.replace(" ", "+") + API_KEY);
        DadosSerie dados = conversor.obterDados(json, DadosSerie.class);
        return dados;
    }

    private void buscarEpisodioPorSerie(){
        DadosSerie dadosSerie = getDadosSerie();
        List<DadosTemporada> temporadas = new ArrayList<>();

        for (int i = 1; i <= dadosSerie.totalTemporadas(); i++) {
            var json = consumo.obterDados(ENDERECO + dadosSerie.titulo().replace(" ", "+") + "&season=" + i + API_KEY);
            DadosTemporada dadosTemporada = conversor.obterDados(json, DadosTemporada.class);
            temporadas.add(dadosTemporada);
        }
        temporadas.forEach(System.out::println);
    }

    private void buscarSeriesPorAtor(){
        System.out.println("Digite o nome do ator para buscar suas séries:");
        var nomeAtor =leitura.nextLine();

        List<Serie> seriesDoAtor = this.dadosSeries.stream()
                .filter(d -> d.atores() != null && d.atores().toLowerCase().contains(nomeAtor.toLowerCase()))
                .map(Serie::new)
                .collect(Collectors.toList());



        if (seriesDoAtor.isEmpty()){
            System.out.println("Nenhum serie encontrado para o ator: " + nomeAtor);
        } else {
            System.out.println("\nSéries encontradas para o ator: " + nomeAtor + ":");
            seriesDoAtor.forEach(s -> {
                System.out.println("Titulo: " + s.getTitulo() +
                        " | Atores: " + s.getAtores());
            });
        }
    }


    private void listarAtoresBuscados(){
        List<Serie> seriesDoAtor = repositorio.findAll();
        seriesDoAtor.stream()
                .filter(a -> a.getAtores() != null && !a.getAtores().isEmpty())
                .forEach(System.out::println);
    }



    private void listarSeriesBuscadas(){
        List<Serie> series = new ArrayList<>();
        series = repositorio.findAll();
        series.stream()
                .sorted(Comparator.comparing(Serie::getGenero))
                .forEach(System.out::println);
    }
}