package fr.eni.lacriptedujeu.services;

import java.util.List;

public interface ICrudService<T> {
     void save(T entity);
    List<T> getAll(List<String> filters, int page, int size);
    T getById(int id);
    void update(int id, T entity);
    void delete(int id);

}
