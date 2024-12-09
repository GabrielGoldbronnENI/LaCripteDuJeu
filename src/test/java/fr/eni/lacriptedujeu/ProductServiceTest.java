package fr.eni.lacriptedujeu;

import fr.eni.lacriptedujeu.exceptions.NotFoundException;
import fr.eni.lacriptedujeu.models.AgeLimit;
import fr.eni.lacriptedujeu.models.Product;
import fr.eni.lacriptedujeu.repositorys.ProductRepository;
import fr.eni.lacriptedujeu.services.ProductService;
import fr.eni.lacriptedujeu.services.ProductServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class ProductServiceTest {

    private static final Logger logger = LoggerFactory.getLogger(ProductServiceTest.class);

    @MockitoBean
    private ProductRepository productRepository;

    @Autowired
    private ProductServiceImpl productService;

    @Test
    void testGetProduct() {
        logger.info("Get product from database");
        Product foundProduct = null;

        try {
            foundProduct = productService.getById(1);
            logger.info("[SUCCESS] Produit trouvé: {}", foundProduct);
        } catch (NotFoundException exception) {
            logger.error("Produit not found: {}", exception.getMessage());
        }


    }

    @Test
    void testSaveProduct() {
        logger.info("Save product from database");
        Product product = new Product();
        product.setTitle("Game 1");

        AgeLimit ageLimit = new AgeLimit();
        ageLimit.setAgeLimitID(1);
        ageLimit.setLabel("3+");
        product.setAgeLimit(ageLimit);

        product.setTariff(BigDecimal.valueOf(19.99));


        try {

            productRepository.save(product);
            logger.info("[SUCCESS] Produit avec sauvegarde: {}", product);
        } catch (Exception exception) {
            logger.error("Produit avec sauvegarde: {}", exception.getMessage());
        }


        List<Product> allproduct = productRepository.getAll(null);
        logger.info("allproduct = {}", allproduct);
    }

    @Test
    void testGetProductNotFound() {
        logger.info("Get product not found");
        int nonExistentProductId = 999;
        when(productRepository.getById(nonExistentProductId)).thenReturn(null);

        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            productService.getById(nonExistentProductId);
        });

        String expectedMessage = "Le Produit avec l'ID " + nonExistentProductId + " est introuvable";
        String actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage);
        logger.info("[SUCCESS] Opération réussie.");
        logger.info("[SUCCESS] {}", exception.getMessage());

        verify(productRepository, times(1)).getById(nonExistentProductId);
    }
}
