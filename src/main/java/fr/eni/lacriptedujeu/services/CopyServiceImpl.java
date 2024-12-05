package fr.eni.lacriptedujeu.services;

import fr.eni.lacriptedujeu.exceptions.LinkedException;
import fr.eni.lacriptedujeu.exceptions.NotFoundException;
import fr.eni.lacriptedujeu.models.Copy;
import fr.eni.lacriptedujeu.repositorys.CopyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CopyServiceImpl implements CopyService {

    private final CopyRepository copyRepository;

    @Autowired
    public CopyServiceImpl(CopyRepository copyRepository) {
        this.copyRepository = copyRepository;
    }

    public void save(Copy copy) {
        try {
            copyRepository.save(copy);
        } catch (DataIntegrityViolationException e) {
            String errorMessage = e.getRootCause() != null ? e.getRootCause().getMessage() : e.getMessage();
            throw new RuntimeException("Erreur lors de la sauvegarde de la copie: " + errorMessage);
        }
    }

    public List<Copy> getAll(List<String> filters) {
        return copyRepository.getAll(filters);
    }

    public Copy getById(int copyID) {
        Copy copy = copyRepository.getById(copyID);
        if (copy == null) {
            throw new NotFoundException("La copie avec l'ID " + copyID + " est introuvable");
        }
        return copy;
    }

    public void update(int copyID, Copy copy) {
        try {
            copyRepository.update(copyID, copy);
        } catch (DataIntegrityViolationException e) {
            String errorMessage = e.getRootCause() != null ? e.getRootCause().getMessage() : e.getMessage();
            throw new RuntimeException("Erreur lors de la mise à jour de la copie: " + errorMessage);
        }
    }

    public void delete(int copyID) {
        try {
            copyRepository.delete(copyID);
        } catch (NotFoundException | LinkedException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Une erreur inattendue s'est produite lors de la suppression de l'exemplaire : " + e.getMessage());
        }
    }
}
