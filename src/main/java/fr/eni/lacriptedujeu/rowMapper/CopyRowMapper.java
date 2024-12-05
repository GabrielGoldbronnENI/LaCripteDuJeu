package fr.eni.lacriptedujeu.rowMapper;

import fr.eni.lacriptedujeu.models.Copy;
import fr.eni.lacriptedujeu.models.Product;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CopyRowMapper implements RowMapper<Copy> {

    private final JdbcTemplate jdbcTemplate;

    public CopyRowMapper(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Copy mapRow(ResultSet rs, int rowNum) throws SQLException {
        Copy copy = new Copy();
        copy.setCopyID(rs.getInt("copy_id"));
        copy.setBarcode(rs.getString("barcode"));
        copy.setStatus(rs.getBoolean("status"));
        copy.setProductID(rs.getInt("product_id"));

        String productQuery = """
            SELECT p.product_id, p.title
            FROM products p
            WHERE p.product_id = ?
        """;
        try {
            Product productDetails = jdbcTemplate.queryForObject(productQuery, (productRs, productRowNum) -> {
                Product product = new Product();
                product.setProductId(productRs.getInt("product_id"));
                product.setTitle(productRs.getString("title"));
                return product;
            }, copy.getProductID());

            copy.setProductDetails(productDetails);
        } catch (Exception e) {
            copy.setProductDetails(null);
        }

        return copy;
    }
}
