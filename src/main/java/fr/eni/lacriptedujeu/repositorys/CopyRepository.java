package fr.eni.lacriptedujeu.repositorys;

import fr.eni.lacriptedujeu.models.Copy;

import java.util.List;

public interface CopyRepository extends ICrudRepository<Copy> {
    public List<Copy> getAllAvailable();
    public int getTotalCount(List<String> filters);
}