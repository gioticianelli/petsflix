package br.com.alura.petsflix.repository;

import br.com.alura.petsflix.model.Serie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SerieRepository extends JpaRepository<Serie, Long> {



}
