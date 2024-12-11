package fr.eni.lacriptedujeu.services;


import fr.eni.lacriptedujeu.models.Copy;

import java.util.List;

public interface CopyService extends ICrudService<Copy> {
    public List<Copy> getAllAvailable();
    public int getTotalCount(List<String> filters);
}
