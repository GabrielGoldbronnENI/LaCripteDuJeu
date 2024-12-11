package fr.eni.lacriptedujeu.rowMapper;

import fr.eni.lacriptedujeu.models.*;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LocationRowMapper implements RowMapper<Location> {

    private final JdbcTemplate jdbcTemplate;

    public LocationRowMapper(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Location mapRow(ResultSet rs, int rowNum) throws SQLException {
        Location location = new Location();

        location.setLocationID(rs.getLong("location_id"));
        location.setPrice(rs.getBigDecimal("price"));
        location.setRentalStatusID(rs.getInt("rental_status_id"));
        location.setCreatedAt(rs.getTimestamp("createdAt").toLocalDateTime());
        location.setUpdatedAt(rs.getTimestamp("updatedAt").toLocalDateTime());


        int userID = rs.getInt("user_id");
        String userQuery = """
                    SELECT *
                    FROM users
                    WHERE user_id = ?
                """;
        location.setUser(jdbcTemplate.queryForObject(userQuery, (rsUser, rowNumUser) -> {
            User user = new User();
            user.setUserID(rsUser.getInt("user_id"));
            user.setFirstName(rsUser.getString("first_name"));
            user.setLastName(rsUser.getString("last_name"));
            user.setEmail(rsUser.getString("email"));
            user.setPhone(rsUser.getString("phone"));
            user.setAddress(rsUser.getString("address"));
            return user;
        }, userID));


        int copyID = rs.getInt("copy_id");
        String copyQuery = """
                    SELECT *
                    FROM copy
                    WHERE copy_id = ?
                """;
        location.setCopy(jdbcTemplate.queryForObject(copyQuery, (rsCopy, rowNumCopy) -> {
            Copy copy = new Copy();
            copy.setCopyID(rsCopy.getInt("copy_id"));
            copy.setBarcode(rsCopy.getString("barcode"));
            copy.setStatus(rsCopy.getBoolean("status"));
            int productID = rsCopy.getInt("product_id");

            copy.setProductID(productID);
            String productQuery = """
                        SELECT product_id, title
                        FROM products
                        WHERE product_id = ?
                    """;
            copy.setProductDetails(jdbcTemplate.queryForObject(productQuery, (rsProduct, rowNumProduct) -> {
                Product product = new Product();
                product.setProductId(rsProduct.getInt("product_id"));
                product.setTitle(rsProduct.getString("title"));
                return product;
            }, productID));

            return copy;
        }, copyID));


        return location;
    }
}
