package fr.eni.lacriptedujeu.services;

import fr.eni.lacriptedujeu.exceptions.LinkedException;
import fr.eni.lacriptedujeu.exceptions.NotFoundException;
import fr.eni.lacriptedujeu.models.Product;
import fr.eni.lacriptedujeu.repositorys.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void save(Product product) {

        try {
            productRepository.save(product);
        } catch (DataIntegrityViolationException e) {
            String errorMessage = e.getRootCause() != null ? e.getRootCause().getMessage() : e.getMessage();
            throw new RuntimeException(errorMessage);
        }
    }

    public List<Product> getAll(List<String> filters) {
        return productRepository.getAll(filters);
    }

    public Product getById(int productID) {
        Product product = productRepository.getById(productID);
        if (product == null) {
            throw new NotFoundException("Le Produit avec l'ID " + productID + " est introuvable");
        }
        return product;
    }

    public void update(int productID, Product product) {
        try {
            productRepository.update(productID,product);

        }  catch (DataIntegrityViolationException e) {
            String errorMessage = e.getRootCause() != null ? e.getRootCause().getMessage() : e.getMessage();
            throw new RuntimeException(errorMessage);
        }
    }

    public void delete(int productID) {
        try {
            productRepository.delete(productID);
        } catch (NotFoundException | LinkedException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Une erreur inattendue s'est produite lors de la suppression : " + e.getMessage());
        }
    }
}
