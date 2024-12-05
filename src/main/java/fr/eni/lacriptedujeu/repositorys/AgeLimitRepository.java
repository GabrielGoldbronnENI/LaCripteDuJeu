package fr.eni.lacriptedujeu.repositorys;

import fr.eni.lacriptedujeu.models.AgeLimit;

import java.util.List;

public interface AgeLimitRepository {
    List<AgeLimit> getAll();
}