package fr.eni.lacriptedujeu.services;

import fr.eni.lacriptedujeu.models.Genre;
import fr.eni.lacriptedujeu.repositorys.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GenreServiceImpl implements GenreService {

    private final GenreRepository genreRepository;

    @Autowired
    public GenreServiceImpl(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }


    public List<Genre> getAll() {
        return genreRepository.getAll();
    }
}
