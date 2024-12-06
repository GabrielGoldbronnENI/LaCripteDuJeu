package fr.eni.lacriptedujeu.services;


import fr.eni.lacriptedujeu.models.AgeLimit;

import java.util.List;

public interface AgeLimitService {
    List<AgeLimit> getAll();
    AgeLimit getById(int ageLimitID);
}
