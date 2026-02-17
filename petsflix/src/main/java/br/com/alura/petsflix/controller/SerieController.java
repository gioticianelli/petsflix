package br.com.alura.petsflix.controller;


import br.com.alura.petsflix.dto.EpisodioDTO;
import br.com.alura.petsflix.dto.SerieDTO;
import br.com.alura.petsflix.model.Serie;
import br.com.alura.petsflix.repository.SerieRepository;
import br.com.alura.petsflix.service.SerieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController //Indica que é uma classe controller
@RequestMapping ("/series") //quando se tem certeza do endpoind pode se utilizar do requestmapping paa já deixar predefinido que todos terão /seri e só mudará a derivação, por exemplo top5
public class SerieController {
    @Autowired
    private SerieService servico;


    @GetMapping //É requisição do tipo GET
    public List<SerieDTO> obterSeries(){
        return servico.obterTodasAsSeries();
    }


    @GetMapping("/top5")
    public List<SerieDTO> obterSeriesTop5(){
        return servico.obterTop5Serie();
    }


    @GetMapping("/lancamentos")
    public List<SerieDTO> obterLancamentos(){
        return servico.obterLancamentos();
    }

    @GetMapping("/{id}")
    public SerieDTO obterSeriePorId(@PathVariable Long id){
        return servico.obterPorId(id);
    }

    @GetMapping("/{id}/temporadas/todas")
    public List<EpisodioDTO> obterTodasTemporadas(@PathVariable Long id){
        return servico.obterTodasAsTemporadas(id);
    }

    @GetMapping("/{id}/temporadas/{numero}")
    public List<EpisodioDTO> obterTemporadasPorNumero(@PathVariable Long id, @PathVariable Long numero){
        return servico.obterTemporadaPorNumero(id, numero);
    }

    @GetMapping("/categoria/{nomeGenero}")
    public List<SerieDTO> obterSeriesPorCategoria(@PathVariable String nomeGenero){
        return servico.obterSeriesPorCategoria(nomeGenero);
    }
}
