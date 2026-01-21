package br.com.alura.petsflix.principal;

import br.com.alura.petsflix.model.DadosEpisodio;
import br.com.alura.petsflix.model.DadosSerie;
import br.com.alura.petsflix.model.DadosTemporada;
import br.com.alura.petsflix.model.Episodio;
import br.com.alura.petsflix.service.ConsumoApi;
import br.com.alura.petsflix.service.ConverteDados;

import java.sql.SQLOutput;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class Principal {
    private Scanner leitura = new Scanner(System.in);
    private ConsumoApi consumo = new ConsumoApi();
    private ConverteDados conversor = new ConverteDados();
    private final String ENDERECO = "Https://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey=934dcb35";


    public void exibeMenu() {
        System.out.println("--- MENU ---");
        System.out.println("Qual série você está buscando?");
        var nomeSerie = leitura.nextLine();
        var json = consumo.obterDados(ENDERECO + nomeSerie.replace(" ", "+") + API_KEY);
        DadosSerie dados = conversor.obterDados(json, DadosSerie.class);
        System.out.println(dados);

        List<DadosTemporada> temporadas = new ArrayList<>();

        //estrutura de repeticao para os episodios
        for (int i = 1; i <= dados.totalTemporadas(); i++) {
            json = consumo.obterDados(ENDERECO + nomeSerie.replace(" ", "+") + "&season=" + i + API_KEY);
            DadosTemporada dadosTemporada = conversor.obterDados(json, DadosTemporada.class);
            temporadas.add(dadosTemporada);

        }
        temporadas.forEach(System.out::println);

        for (int i = 0; i < dados.totalTemporadas(); i++) {
            List<DadosEpisodio> episodiosTemporada = temporadas.get(i).episodios();
            for (int j = 0; j < episodiosTemporada.size(); j++) {
                System.out.println(episodiosTemporada.get(j).titulo());
            }
        }


        //lambdas - funcoes que terão um parametro (pode ser as letrsa), e quando for trabalahr com coleções, chama o parametro +  algo
        //o java ja entende oq será feito (t - temp) (e - epi)
        temporadas.forEach(t -> t.episodios().forEach(e -> System.out.println(e.titulo())));

        List<String> nomes = Arrays.asList("Giiovana", "Kiyra", "Mel", "Fred");

        nomes.stream()
                .sorted()
                .limit(5)
                .filter(n -> n.startsWith("G"))
                .map(n -> n.toUpperCase())
                .forEach(System.out::println);


        List<DadosEpisodio> dadosEpisodios = temporadas.stream()
                .flatMap(t -> t.episodios().stream())
                .collect(Collectors.toList());
        //.toList(); //imutavel

        System.out.println("\n Top 5 aupisódios");
        dadosEpisodios.stream()
                .filter(e -> !e.avaliacao().equalsIgnoreCase("N/A"))
                .peek(e -> System.out.println("PRIMEIRO FILTRO! (N/A) -------" + e))
                .sorted(Comparator.comparing(DadosEpisodio::avaliacao).reversed())
                .limit(5)
                .forEach(System.out::println);

        List<Episodio> episodios = temporadas.stream()
                .flatMap(t -> t.episodios().stream()
                        .map(d -> new Episodio(t.numero(), d)))
                .collect(Collectors.toList());

        episodios.forEach(System.out::println);

        System.out.println("Qual episódio você quer assitir?");
        var trechoTitulo = leitura.nextLine();
        Optional<Episodio> episodioBuscado = episodios.stream()
                .filter(e -> e.getTitulo().toUpperCase().contains(trechoTitulo.toUpperCase()))
                .findFirst();
        if (episodioBuscado.isPresent()) {
            System.out.println("Episódio encontrado!");
            System.out.println("Temporada: "  + episodioBuscado.get().getTemporada());
        } else {
            System.out.println("Episódio não encontrado.");
        }


        //filtro por ano os episodios
        System.out.println("A partir de que ano você deseja ver os episodios? ");
        var ano = leitura.nextInt();
        leitura.nextLine();

         //data formatada dd/mm/yyyy
        LocalDate dataBusca = LocalDate.of(ano, 1, 1);
        DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");


        //formatacao
        episodios.stream()
                .filter(e ->e.getDataLancamento() != null && e.getDataLancamento().isAfter(dataBusca))
                .forEach(e -> System.out.println("Temporada: " + e.getTemporada() +
                        " Episodio: " + e.getTitulo() +
                        " Data de laçamento: " + e.getDataLancamento().format(formatador)

                ));


        //avaliacao por temporadas
        Map<Integer, Double> avaliacoesPorTemporada = episodios.stream()
                .filter(e -> e.getAvaliacao() > 0.0)
                .collect(Collectors.groupingBy(Episodio::getTemporada,
                        Collectors.averagingDouble(Episodio::getAvaliacao)));
        System.out.println(avaliacoesPorTemporada);


        //gerar estatiticas
        DoubleSummaryStatistics est = episodios.stream()
                .filter(e -> e.getAvaliacao() > 0.0)
                .collect(Collectors.summarizingDouble(Episodio::getAvaliacao));
        System.out.println(est);
        System.out.println("Média: " + est.getAverage());
        System.out.println("Pior nota: " + est.getMin());
        System.out.println("Melhor notas: " + est.getMax());
        System.out.println("Total de notas: " + est.getCount());

    }

}
