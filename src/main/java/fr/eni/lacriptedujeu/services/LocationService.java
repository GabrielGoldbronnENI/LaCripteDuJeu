package fr.eni.lacriptedujeu.services;


import fr.eni.lacriptedujeu.models.Location;

import java.util.List;

public interface LocationService extends ICrudService<Location> {
    public int getTotalCount(List<String> filters);
}
