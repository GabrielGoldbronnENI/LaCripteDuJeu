package fr.eni.lacriptedujeu.converter;

import fr.eni.lacriptedujeu.models.Genre;
import fr.eni.lacriptedujeu.services.GenreService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToGenreConverter implements Converter<String, Genre> {

    private static final Logger logger = LoggerFactory.getLogger(StringToGenreConverter.class);

    private final GenreService genreService;

    @Autowired
    public StringToGenreConverter(GenreService genreService) {
        this.genreService = genreService;
    }

    @Override
    public Genre convert(String strGenreID) {
        if (strGenreID.trim().isEmpty()) {
            logger.error("Invalid Genre ID: input is null or empty");
            throw new IllegalArgumentException("Genre ID cannot be null or empty");
        }

        try {
            int genreID = Integer.parseInt(strGenreID);
            logger.info("Converting Genre ID: {}", genreID);
            return genreService.getById(genreID);
        } catch (NumberFormatException e) {
            logger.error("Invalid Genre ID: '{}' is not a valid number", strGenreID, e);
            throw new IllegalArgumentException("Invalid Genre ID format: must be a numeric value", e);
        } catch (RuntimeException e) {
            logger.error("Genre not found for ID: {}", strGenreID, e);
            throw new IllegalArgumentException("Genre not found for ID: " + strGenreID, e);
        }
    }
}
