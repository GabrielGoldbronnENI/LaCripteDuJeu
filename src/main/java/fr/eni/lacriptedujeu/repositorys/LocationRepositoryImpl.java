package fr.eni.lacriptedujeu.repositorys;

import fr.eni.lacriptedujeu.exceptions.LimitException;
import fr.eni.lacriptedujeu.exceptions.LinkedException;
import fr.eni.lacriptedujeu.exceptions.NotFoundException;
import fr.eni.lacriptedujeu.models.Genre;
import fr.eni.lacriptedujeu.models.Location;
import fr.eni.lacriptedujeu.models.Product;
import fr.eni.lacriptedujeu.rowMapper.LocationRowMapper;
import fr.eni.lacriptedujeu.rowMapper.ProductRowMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Repository
public class LocationRepositoryImpl implements LocationRepository {
    private static final Logger logger = LoggerFactory.getLogger(LocationRepositoryImpl.class);

    @Autowired
    public JdbcTemplate jdbcTemplate;

    @Transactional
    public void save(Location location) {
        // Vérification du nombre de locations en cours pour cet utilisateur
        String limitSql = """
            SELECT COUNT(*)
            FROM locations l
            WHERE l.user_id = ? AND l.rental_status_id = 1;
            """;
        Integer count = jdbcTemplate.queryForObject(limitSql, Integer.class, location.getUser().getUserID());

        if (count != null && count >= 5) {
            throw new LimitException("Un client ne peut avoir que 5 locations en cours");
        }

        // Insertion dans la table 'locations'
        String productSql = """
            INSERT INTO locations(price, user_id, rental_status_id, copy_id) 
            VALUES (?, ?, ?, ?) RETURNING location_id
            """;
        Integer locationID = jdbcTemplate.queryForObject(
                productSql,
                Integer.class,
                location.getCopy().getProductDetails().getTariff(),
                location.getUser().getUserID(),
                location.getRentalStatusID(),
                location.getCopy().getCopyID()
        );

        // Insertion dans la table 'user_location'
        String userSql = "INSERT INTO user_location(user_id, location_id) VALUES (?, ?)";
        jdbcTemplate.update(userSql, location.getUser().getUserID(), locationID);

        // Insertion dans la table 'copy_location'
        String copySql = "INSERT INTO copy_location(copy_id, location_id) VALUES (?, ?)";
        jdbcTemplate.update(copySql, location.getCopy().getCopyID(), locationID);
    }


    public List<Location> getAll(List<String> filters, int page, int size) {
        StringBuilder sql = new StringBuilder("SELECT * FROM locations WHERE 1=1");
        List<Object> params = new ArrayList<>();

        String[] columns = {"copy_id", "user_id", "rental_status_id"};

        if (filters != null && !filters.isEmpty()) {
            for (int i = 0; i < filters.size(); i++) {
                String filter = filters.get(i);
                if (filter != null && !filter.isEmpty()) {
                    sql.append(" AND ").append(columns[i]).append(" = ?");
                    params.add(Integer.parseInt(filter));
                }
            }
        }

        sql.append(" LIMIT ? OFFSET ?");
        params.add(size); // Nombre maximum d'éléments
        params.add(page * size); // Décalage (offset)

        logger.info("Executing SQL: {} with params: {}", sql, params);
        return jdbcTemplate.query(sql.toString(), new LocationRowMapper(jdbcTemplate), params.toArray());
    }


    public Location getById(int locationID) {
        String sql = "SELECT * FROM locations WHERE location_id=?";
        try {
            return jdbcTemplate.queryForObject(sql, new LocationRowMapper(jdbcTemplate), locationID);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Transactional
    public void update(int locationID, Location location) {
        String sql = "UPDATE locations SET price = ?, copy_id = ?, user_id = ?, rental_status_id = ? WHERE location_id = ?";
        jdbcTemplate.update(
                sql,
                location.getCopy().getProductDetails().getTariff(),
                location.getCopy().getCopyID(),
                location.getUser().getUserID(),
                location.getRentalStatusID(),
                locationID
        );

        String deleteUserSql = "DELETE FROM user_location WHERE location_id = ?";
        jdbcTemplate.update(deleteUserSql, locationID);
        String userSql = "INSERT INTO user_location(user_id, location_id) VALUES (?, ?)";
        jdbcTemplate.update(userSql, location.getUser().getUserID(), locationID);

        String deleteCopySql = "DELETE FROM copy_location WHERE location_id = ?";
        jdbcTemplate.update(deleteCopySql, locationID);
        String copySql = "INSERT INTO copy_location(copy_id, location_id) VALUES (?, ?)";
        jdbcTemplate.update(copySql, location.getCopy().getCopyID(), locationID);
    }


    public void delete(int locationID) {
        String deleteQuery = "DELETE FROM locations WHERE location_id = ?";
        int rowsAffected = jdbcTemplate.update(deleteQuery, locationID);

        if (rowsAffected == 0) {
            throw new NotFoundException("La location avec l'ID " + locationID + " est introuvable.");
        }
    }

}