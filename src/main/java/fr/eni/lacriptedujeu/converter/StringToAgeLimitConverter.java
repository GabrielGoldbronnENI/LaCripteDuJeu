package fr.eni.lacriptedujeu.converter;

import fr.eni.lacriptedujeu.models.AgeLimit;
import fr.eni.lacriptedujeu.services.AgeLimitService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToAgeLimitConverter implements Converter<String, AgeLimit> {

    private static final Logger logger = LoggerFactory.getLogger(StringToAgeLimitConverter.class);

    private final AgeLimitService ageLimitService;

    @Autowired
    public StringToAgeLimitConverter(AgeLimitService ageLimitService) {
        this.ageLimitService = ageLimitService;
    }

    @Override
    public AgeLimit convert(String strAgeLimitID) {
        if (strAgeLimitID.trim().isEmpty()) {
            logger.error("Invalid Age limit ID: input is null or empty");
            throw new IllegalArgumentException("Age limit ID cannot be null or empty");
        }

        try {
            int ageLimitID = Integer.parseInt(strAgeLimitID);
            logger.info("Converting Age limi ID: {}", ageLimitID);
            return ageLimitService.getById(ageLimitID);
        } catch (NumberFormatException e) {
            logger.error("Invalid Age limit ID: '{}' is not a valid number", strAgeLimitID, e);
            throw new IllegalArgumentException("Invalid Age limit ID format: must be a numeric value", e);
        } catch (RuntimeException e) {
            logger.error("Age limit not found for ID: {}", strAgeLimitID, e);
            throw new IllegalArgumentException("Age limit not found for ID: " + strAgeLimitID, e);
        }
    }
}
