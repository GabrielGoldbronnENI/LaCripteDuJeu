package fr.eni.lacriptedujeu.repositorys;

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
        String productSql = "INSERT INTO locations(price, product_id, user_id, rental_status_id) RETURNING location_id";
        int locationID = jdbcTemplate.queryForObject(productSql, Integer.class, location.getPrice(), location.getProductID(), location.getUserID(), location.getRentalStatusID());

        String userSql = "INSERT INTO user_location(user_id, location_id) VALUES (?, ?)";
        jdbcTemplate.update(userSql, location.getUserID(), locationID);

        String copySql = "INSERT INTO copy_location(copy_id, location_id) VALUES (?, ?)";
        jdbcTemplate.update(copySql, location.getCopyID(), locationID);
    }


    public List<Location> getAll(List<String> filters) {
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

        return jdbcTemplate.query(sql.toString(), new LocationRowMapper(), params.toArray());
    }


    public Location getById(int locationID) {
        String sql = "SELECT * FROM locations WHERE location_id=?";
        try {
            return jdbcTemplate.queryForObject(sql, new LocationRowMapper(), locationID);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Transactional
    public void update(int locationID, Location location) {
        String sql = "UPDATE locations SET price = ?, product_id = ?, user_id = ?, rental_status_id = ? WHERE location_id = ?";
        jdbcTemplate.update(sql, location.getPrice(), location.getProductID(), location.getUserID(), location.getRentalStatusID(), locationID);

        String deleteUserSql = "DELETE FROM user_location WHERE location_id = ?";
        jdbcTemplate.update(deleteUserSql, locationID);
        String userSql = "INSERT INTO user_location(user_id, location_id) VALUES (?, ?)";
        jdbcTemplate.update(userSql, location.getUserID(), locationID);

        String deleteCopySql = "DELETE FROM copy_location WHERE location_id = ?";
        jdbcTemplate.update(deleteCopySql, locationID);
        String copySql = "INSERT INTO copy_location(copy_id, location_id) VALUES (?, ?)";
        jdbcTemplate.update(copySql, location.getCopyID(), locationID);
    }


    public void delete(int locationID) {
        String deleteQuery = "DELETE FROM locations WHERE location_id = ?";
        int rowsAffected = jdbcTemplate.update(deleteQuery, locationID);

        if (rowsAffected == 0) {
            throw new NotFoundException("La location avec l'ID " + locationID + " est introuvable.");
        }
    }

}