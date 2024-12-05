package fr.eni.lacriptedujeu.rowMapper;

import fr.eni.lacriptedujeu.models.AgeLimit;
import fr.eni.lacriptedujeu.models.Genre;
import fr.eni.lacriptedujeu.models.Product;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ProductRowMapper implements RowMapper<Product> {

    private final JdbcTemplate jdbcTemplate;

    public ProductRowMapper(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
        Product product = new Product();
        product.setProductId(rs.getInt("product_id"));
        product.setTitle(rs.getString("title"));
        product.setPlayTime(rs.getString("play_time"));
        product.setTariff(rs.getBigDecimal("tariff"));
        product.setAgeLimit(rs.getInt("age_limit"));

        String ageLimitQuery = """
            SELECT age_limit_id, label
            FROM age_limit
            WHERE age_limit_id = ?
        """;
        AgeLimit ageLimit = jdbcTemplate.queryForObject(ageLimitQuery, (rsAge, rowNumAge) -> {
            AgeLimit limit = new AgeLimit();
            limit.setAgeLimitID(rsAge.getInt("age_limit_id"));
            limit.setLabel(rsAge.getString("label"));
            return limit;
        }, product.getAgeLimit());
        product.setAgeLimitDetails(ageLimit);


        String genreQuery = """
            SELECT g.genre_id, g.title
            FROM product_genre pg
            JOIN genre g ON pg.genre_id = g.genre_id
            WHERE pg.product_id = ?
        """;

        List<Genre> genres = jdbcTemplate.query(genreQuery, (rsGenre, rowNumGenre) -> {
            Genre genre = new Genre();
            genre.setGenreID(rsGenre.getInt("genre_id"));
            genre.setTitle(rsGenre.getString("title"));
            return genre;
        }, product.getProductID());

        product.setGenresDetails(genres);


        List<Integer> genreIds = jdbcTemplate.query(genreQuery, (rsGenre, rowNumGenre) ->
                        rsGenre.getInt("genre_id"),
                product.getProductID()
        );

        product.setGenres(genreIds);

        return product;
    }
}