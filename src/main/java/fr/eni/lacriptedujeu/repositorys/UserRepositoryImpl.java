package fr.eni.lacriptedujeu.repositorys;

import fr.eni.lacriptedujeu.exceptions.LinkedException;
import fr.eni.lacriptedujeu.exceptions.NotFoundException;
import fr.eni.lacriptedujeu.models.User;
import fr.eni.lacriptedujeu.rowMapper.UserRowMapper;
import fr.eni.lacriptedujeu.utils.PhoneNumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class UserRepositoryImpl implements UserRepository {
    private static final Logger logger = LoggerFactory.getLogger(UserRepositoryImpl.class);

    @Autowired
    JdbcTemplate jdbcTemplate;

    public void save(User user) {


        var firstName = user.getFirstName() != null ? user.getFirstName().toLowerCase() : null;
        var lastName = user.getLastName() != null ? user.getLastName().toLowerCase() : null;
        var email = user.getEmail() != null ? user.getEmail().toLowerCase() : null;
        user.setPhone(PhoneNumberUtils.normalizePhoneNumber(user.getPhone()));

        String sql = "INSERT INTO users(first_name, last_name, email, phone, address) VALUES(?,?,?,?,?)";
        jdbcTemplate.update(sql, firstName, lastName, email, user.getPhone(), user.getAddress());
    }


    public List<User> getAll(List<String> filters, int page, int size) {
        StringBuilder sql = new StringBuilder("SELECT * FROM users WHERE 1=1");
        List<Object> params = new ArrayList<>();

        String[] columns = {"first_name", "last_name", "email", "phone"};

        if (filters != null && !filters.isEmpty()) {
            for (int i = 0; i < filters.size(); i++) {
                String filter = filters.get(i);
                if (filter != null && !filter.isEmpty()) {
                    sql.append(" AND LOWER(").append(columns[i]).append(") LIKE ?");
                    params.add("%" + filter.toLowerCase() + "%");
//                    "AND LOWER(first_name) LIKE ? %NAME%"
                }
            }
        }

        sql.append(" LIMIT ? OFFSET ?");
        params.add(size); // Nombre maximum d'éléments
        params.add(page * size); // Décalage (offset)

        logger.info("Executing SQL: {} with params: {}", sql, params);
        return jdbcTemplate.query(sql.toString(), new UserRowMapper(), params.toArray());
    }



    public User getById(int userID) {
        String sql = "SELECT * FROM users WHERE user_id=?";
        try {
            return jdbcTemplate.queryForObject(sql, new UserRowMapper(), userID);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public void update(int userID, User user) {
        var firstName = user.getFirstName() != null ? user.getFirstName().toLowerCase() : null;
        var lastName = user.getLastName() != null ? user.getLastName().toLowerCase() : null;
        var email = user.getEmail() != null ? user.getEmail().toLowerCase() : null;
        user.setPhone(PhoneNumberUtils.normalizePhoneNumber(user.getPhone()));

        String getEmailSql = "SELECT email FROM users WHERE user_id = ?";
        String currentEmail = jdbcTemplate.queryForObject(getEmailSql, String.class, userID);

        if (email != null && email.equalsIgnoreCase(currentEmail)) {
            String updateSql = "UPDATE users SET first_name = ?, last_name = ?, phone = ?, address = ? WHERE user_id = ?";
            jdbcTemplate.update(updateSql, firstName, lastName, user.getPhone(), user.getAddress(), userID);;
        } else {
            String updateSql = "UPDATE users SET first_name = ?, last_name = ?, email = ?, phone = ?, address = ? WHERE user_id = ?";
            jdbcTemplate.update(updateSql, firstName, lastName, email, user.getPhone(), user.getAddress(), userID);
        }
    }


    public void delete(int userID) {
        String checkQuery = """
                    SELECT COUNT(*) 
                    FROM locations l
                    WHERE l.user_id = ? AND l.rental_status_id = 1
                """;
        Integer count = jdbcTemplate.queryForObject(checkQuery, Integer.class, userID);

        if (count != null && count > 0) {
            throw new LinkedException("L'utilisateur ne peut pas être supprimé car il a une location en cours.");
        }

        String deleteQuery = "DELETE FROM users WHERE user_id = ?";
        int rowsAffected = jdbcTemplate.update(deleteQuery, userID);

        if (rowsAffected == 0) {
            throw new NotFoundException("L'utilisateur avec l'ID " + userID + " est introuvable.");
        }
    }

}
