package fr.eni.lacriptedujeu.repositorys;


import fr.eni.lacriptedujeu.models.User;

import java.util.List;

public interface ICrudRepository<T> {
    void save(T entity);
    List<T> getAll(List<String> filters);
    T getById(int id);
    void update(int id, T entity);
    void delete(int id);
}