package fr.eni.lacriptedujeu.services;

import fr.eni.lacriptedujeu.exceptions.LinkedException;
import fr.eni.lacriptedujeu.exceptions.NotFoundException;
import fr.eni.lacriptedujeu.models.Location;
import fr.eni.lacriptedujeu.models.Product;
import fr.eni.lacriptedujeu.repositorys.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocationServiceImpl implements LocationService {

    private final LocationRepository locationRepository;

    @Autowired
    public LocationServiceImpl(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    public void save(Location location) {

        try {
            locationRepository.save(location);
        } catch (DataIntegrityViolationException e) {
            String errorMessage = e.getRootCause() != null ? e.getRootCause().getMessage() : e.getMessage();
            throw new RuntimeException(errorMessage);
        }
    }

    public List<Location> getAll(List<String> filters, int page, int size) {
        try {
            return locationRepository.getAll(filters, page, size);
        } catch (DataIntegrityViolationException e) {
            String errorMessage = e.getRootCause() != null ? e.getRootCause().getMessage() : e.getMessage();
            throw new RuntimeException(errorMessage);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Location getById(int productID) {
        Location location = locationRepository.getById(productID);
        if (location == null) {
            throw new NotFoundException("Le Produit avec l'ID " + productID + " est introuvable");
        }
        return location;
    }

    public void update(int locationID, Location location) {
        try {
            locationRepository.update(locationID, location);
        }  catch (DataIntegrityViolationException e) {
            String errorMessage = e.getRootCause() != null ? e.getRootCause().getMessage() : e.getMessage();
            throw new RuntimeException(errorMessage);
        }
    }

    public void delete(int locationID) {
        try {
            locationRepository.delete(locationID);
        } catch (NotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Une erreur inattendue s'est produite lors de la suppression : " + e.getMessage());
        }
    }
}
