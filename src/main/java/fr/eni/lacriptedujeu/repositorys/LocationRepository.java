package fr.eni.lacriptedujeu.repositorys;

import fr.eni.lacriptedujeu.models.Location;

import java.util.List;

public interface LocationRepository extends ICrudRepository<Location> {
    int getTotalCount(List<String> filters);
}