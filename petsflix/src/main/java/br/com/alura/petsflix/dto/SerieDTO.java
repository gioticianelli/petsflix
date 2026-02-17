package br.com.alura.petsflix.dto;


import br.com.alura.petsflix.model.Categoria;

public record SerieDTO(Long id, String titulo, Integer totalTemporadas, Double avaliacao,
                       Categoria genero, String atores, String poster, String sinopse) {


}
