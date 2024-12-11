package fr.eni.lacriptedujeu.converter;

import fr.eni.lacriptedujeu.models.User;
import fr.eni.lacriptedujeu.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToUserConverter implements Converter<String, User> {

    private static final Logger logger = LoggerFactory.getLogger(StringToUserConverter.class);

    private final UserService userService;

    @Autowired
    public StringToUserConverter(UserService userService) {
        this.userService = userService;
    }

    @Override
    public User convert(String strUserID) {
        if (strUserID.trim().isEmpty()) {
            logger.error("Invalid User ID: input is null or empty");
            throw new IllegalArgumentException("User ID cannot be null or empty");
        }

        try {
            int userID = Integer.parseInt(strUserID);
            logger.info("Converting User ID: {}", userID);
            return userService.getById(userID);
        } catch (NumberFormatException e) {
            logger.error("Invalid user ID: '{}' is not a valid number", strUserID, e);
            throw new IllegalArgumentException("Invalid user ID format: must be a numeric value", e);
        } catch (RuntimeException e) {
            logger.error("User not found for ID: {}", strUserID, e);
            throw new IllegalArgumentException("User not found for ID: " + strUserID, e);
        }
    }
}
