package fr.eni.lacriptedujeu.repositorys;

import fr.eni.lacriptedujeu.exceptions.LinkedException;
import fr.eni.lacriptedujeu.exceptions.NotFoundException;
import fr.eni.lacriptedujeu.models.Genre;
import fr.eni.lacriptedujeu.models.Product;
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
public class ProductRepositoryImpl implements ProductRepository {
    private static final Logger logger = LoggerFactory.getLogger(ProductRepositoryImpl.class);

    @Autowired
    public JdbcTemplate jdbcTemplate;

    @Transactional
    public void save(Product product) {
        var title = product.getTitle() != null ? product.getTitle().toLowerCase() : null;
        var playTime = product.getPlayTime() != null ? product.getPlayTime().toLowerCase() : null;

        String productSql = "INSERT INTO products(title, play_time, tariff, age_limit) VALUES (?, ?, ?, ?) RETURNING product_id";
        int productID = jdbcTemplate.queryForObject(productSql, Integer.class, title, playTime, product.getTariff(), product.getAgeLimit().getAgeLimitID());

        if (product.getGenres() != null) {
            String insertGenreSql = "INSERT INTO product_genre (product_id, genre_id) VALUES (?, ?)";
            for (Genre genre : product.getGenres()) {
                jdbcTemplate.update(insertGenreSql, productID, genre.getGenreID());
            }
        }

        String ageLimitSql = "INSERT INTO product_age_limit(product_id, age_limit_id) VALUES (?, ?)";
        jdbcTemplate.update(ageLimitSql, productID, product.getAgeLimit().getAgeLimitID());
    }


    public List<Product> getAll(List<String> filters) {
        StringBuilder sql = new StringBuilder("SELECT * FROM products WHERE 1=1");
        List<Object> params = new ArrayList<>();

        String[] columns = {"title", "age_limit"};

        if (filters != null && !filters.isEmpty()) {
            for (int i = 0; i < filters.size(); i++) {
                String filter = filters.get(i);
                if (filter != null && !filter.isEmpty()) {
                    if ("age_limit".equals(columns[i])) {
                        sql.append(" AND ").append(columns[i]).append(" = ?");
                        params.add(Integer.parseInt(filter));
                    } else {
                        sql.append(" AND LOWER(").append(columns[i]).append(") LIKE ?");
                        params.add("%" + filter.toLowerCase() + "%");
                    }
                }
            }
        }

        return jdbcTemplate.query(sql.toString(), new ProductRowMapper(jdbcTemplate), params.toArray());
    }


    public Product getById(int productID) {
        String sql = "SELECT * FROM products WHERE product_id=?";
        try {
            return jdbcTemplate.queryForObject(sql, new ProductRowMapper(jdbcTemplate), productID);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Transactional
    public void update(int productID, Product product) {
        // Update product table
        var title = product.getTitle() != null ? product.getTitle().toLowerCase() : null;
        var playTime = product.getPlayTime() != null ? product.getPlayTime().toLowerCase() : null;

        String productSql = "UPDATE products SET title = ?, play_time = ?, tariff = ?, age_limit = ? WHERE product_id = ?";
        jdbcTemplate.update(productSql, title, playTime, product.getTariff(), product.getAgeLimit().getAgeLimitID(), productID);


        String deleteGenresSql = "DELETE FROM product_genre WHERE product_id = ?";
        jdbcTemplate.update(deleteGenresSql, productID);

        if (product.getGenres() != null) {
            String insertGenreSql = "INSERT INTO product_genre (product_id, genre_id) VALUES (?, ?)";
            for (Genre genre : product.getGenres()) {
                jdbcTemplate.update(insertGenreSql, productID, genre.getGenreID());
            }
        }


        String deleteAgeLimitSql = "DELETE FROM product_age_limit WHERE product_id = ?";
        jdbcTemplate.update(deleteAgeLimitSql, productID);

        String insertAgeLimitSql = "INSERT INTO product_age_limit (product_id, age_limit_id) VALUES (?, ?)";
        jdbcTemplate.update(insertAgeLimitSql, productID, product.getAgeLimit().getAgeLimitID());
    }


    public void delete(int productID) {
        String checkQuery = """
                    SELECT COUNT(*) 
                    FROM locations l
                    WHERE l.product_id = ? AND l.rental_status_id = 1
                """;
        Integer count = jdbcTemplate.queryForObject(checkQuery, Integer.class, productID);

        if (count != null && count > 0) {
            throw new LinkedException("Le produit ne peut pas être supprimé car il a une location en cours.");
        }

        String deleteQuery = "DELETE FROM products WHERE product_id = ?";
        int rowsAffected = jdbcTemplate.update(deleteQuery, productID);

        if (rowsAffected == 0) {
            throw new NotFoundException("L'utilisateur avec l'ID " + productID + " est introuvable.");
        }
    }

}