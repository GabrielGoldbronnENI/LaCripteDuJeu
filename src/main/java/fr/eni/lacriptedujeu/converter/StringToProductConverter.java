package fr.eni.lacriptedujeu.converter;

import fr.eni.lacriptedujeu.models.Product;
import fr.eni.lacriptedujeu.services.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToProductConverter implements Converter<String, Product> {

    private static final Logger logger = LoggerFactory.getLogger(StringToProductConverter.class);

    private final ProductService productService;

    @Autowired
    public StringToProductConverter(ProductService productService) {
        this.productService = productService;
    }

    @Override
    public Product convert(String strProductID) {
        if (strProductID.trim().isEmpty()) {
            logger.error("Invalid Product ID: input is null or empty");
            throw new IllegalArgumentException("Product ID cannot be null or empty");
        }

        try {
            int productID = Integer.parseInt(strProductID);
            logger.info("Converting Product ID: {}", productID);
            return productService.getById(productID);
        } catch (NumberFormatException e) {
            logger.error("Invalid Product ID: '{}' is not a valid number", strProductID, e);
            throw new IllegalArgumentException("Invalid Product ID format: must be a numeric value", e);
        } catch (RuntimeException e) {
            logger.error("Product not found for ID: {}", strProductID, e);
            throw new IllegalArgumentException("Product not found for ID: " + strProductID, e);
        }
    }
}
