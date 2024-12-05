package fr.eni.lacriptedujeu.repositorys;

import fr.eni.lacriptedujeu.models.Genre;

import java.util.List;

public interface GenreRepository {
    List<Genre> getAll();
}