package fr.eni.lacriptedujeu.converter;

import fr.eni.lacriptedujeu.models.Copy;
import fr.eni.lacriptedujeu.services.CopyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToCopyConverter implements Converter<String, Copy> {

    private static final Logger logger = LoggerFactory.getLogger(StringToCopyConverter.class);

    private final CopyService copyService;

    @Autowired
    public StringToCopyConverter(CopyService copyService) {
        this.copyService = copyService;
    }

    @Override
    public Copy convert(String strCopyID) {
        if (strCopyID.trim().isEmpty()) {
            logger.error("Invalid Copy ID: input is null or empty");
            throw new IllegalArgumentException("Copy ID cannot be null or empty");
        }

        try {
            int copyID = Integer.parseInt(strCopyID);
            logger.info("Converting Copy ID: {}", copyID);
            return copyService.getById(copyID);
        } catch (NumberFormatException e) {
            logger.error("Invalid Copy ID: '{}' is not a valid number", strCopyID, e);
            throw new IllegalArgumentException("Invalid Copy ID format: must be a numeric value", e);
        } catch (RuntimeException e) {
            logger.error("Copy not found for ID: {}", strCopyID, e);
            throw new IllegalArgumentException("Copy not found for ID: " + strCopyID, e);
        }
    }
}
