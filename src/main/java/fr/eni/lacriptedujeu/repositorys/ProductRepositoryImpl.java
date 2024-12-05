package fr.eni.lacriptedujeu.repositorys;

import fr.eni.lacriptedujeu.controllers.ProductController;
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
    JdbcTemplate jdbcTemplate;

    @Transactional
    public void save(Product product) {
        var title = product.getTitle() != null ? product.getTitle().toLowerCase() : null;
        var playTime = product.getPlayTime() != null ? product.getPlayTime().toLowerCase() : null;

        String productSql = "INSERT INTO products(title, play_time, tariff, age_limit) VALUES (?, ?, ?, ?) RETURNING product_id";
        int productID = jdbcTemplate.queryForObject(productSql, Integer.class, title, playTime, product.getTariff(), product.getAgeLimit());

        String insertGenreSql = "INSERT INTO product_genre (product_id, genre_id) VALUES (?, ?)";
        for (Integer genreId : product.getGenres()) {
            jdbcTemplate.update(insertGenreSql, productID, genreId);
        }

        String ageLimitSql = "INSERT INTO product_age_limit(product_id, age_limit_id) VALUES (?, ?)";
        jdbcTemplate.update(ageLimitSql, productID, product.getAgeLimit());
    }



    public List<Product> getAll(List<String> filters) {
        StringBuilder sql = new StringBuilder("SELECT * FROM products WHERE 1=1");
        List<Object> params = new ArrayList<>();

        // Corresponding columns for filters
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
        jdbcTemplate.update(productSql, title, playTime, product.getTariff(), product.getAgeLimit(), productID);


        String deleteGenresSql = "DELETE FROM product_genre WHERE product_id = ?";
        jdbcTemplate.update(deleteGenresSql, productID);

        String insertGenreSql = "INSERT INTO product_genre (product_id, genre_id) VALUES (?, ?)";
        for (Integer genre : product.getGenres()) {
            jdbcTemplate.update(insertGenreSql, productID, genre);
        }


        String deleteAgeLimitSql = "DELETE FROM product_age_limit WHERE product_id = ?";
        jdbcTemplate.update(deleteAgeLimitSql, productID);

        String insertAgeLimitSql = "INSERT INTO product_age_limit (product_id, age_limit_id) VALUES (?, ?)";
        jdbcTemplate.update(insertAgeLimitSql, productID, product.getAgeLimit());
    }



    public void delete(int productID) {
        String deleteQuery = "DELETE FROM products WHERE product_id = ?";
        jdbcTemplate.update(deleteQuery, productID);
    }

}