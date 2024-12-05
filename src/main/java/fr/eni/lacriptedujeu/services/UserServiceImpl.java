package fr.eni.lacriptedujeu.services;

import fr.eni.lacriptedujeu.exceptions.LinkedException;
import fr.eni.lacriptedujeu.exceptions.NotFoundException;
import fr.eni.lacriptedujeu.models.User;
import fr.eni.lacriptedujeu.repositorys.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void save(User user) {

        try {
            userRepository.save(user);
        } catch (DataIntegrityViolationException e) {
            String errorMessage = e.getRootCause() != null ? e.getRootCause().getMessage() : e.getMessage();

            if (errorMessage.contains("email")) {
                throw new RuntimeException("L'email est déjà utilisé !");
            } else if (errorMessage.contains("phone")) {
                throw new RuntimeException("Le numéro de téléphone est déjà utilisé !");
            } else {
                throw new RuntimeException("L'email ou le téléphone est déjà utilisé !");
            }
        }
    }

    public List<User> getAll(List<String> filters) {
        return userRepository.getAll(filters);
    }

    public User getById(int userID) {
        User user = userRepository.getById(userID);
        if (user == null) {
            throw new NotFoundException("L'utilisateur avec l'ID " + userID + " est introuvable");
        }
        return user;
    }

    public void update(int userID, User user) {
        try {
            userRepository.update(userID,user);

        } catch (DataIntegrityViolationException e) {
            String errorMessage = e.getRootCause() != null ? e.getRootCause().getMessage() : e.getMessage();

            if (errorMessage.contains("email")) {
                throw new RuntimeException("L'email est déjà utilisé !");
            } else if (errorMessage.contains("phone")) {
                throw new RuntimeException("Le numéro de téléphone est déjà utilisé !");
            } else {
                throw new RuntimeException("L'email ou le téléphone est déjà utilisé !");
            }
        }
    }

    public void delete(int userID) {
        try {
            userRepository.delete(userID);
        } catch (NotFoundException | LinkedException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Une erreur inattendue s'est produite lors de la suppression de l'utilisateur : " + e.getMessage());
        }
    }
}
