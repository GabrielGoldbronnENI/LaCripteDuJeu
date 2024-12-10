package fr.eni.lacriptedujeu.rowMapper;
import fr.eni.lacriptedujeu.models.Location;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LocationRowMapper implements RowMapper<Location> {

    @Override
    public Location mapRow(ResultSet rs, int rowNum) throws SQLException {
        Location location = new Location();

        location.setLocationID(rs.getLong("location_id"));
        location.setPrice(rs.getBigDecimal("price"));
        location.setRentalStatusID(rs.getInt("rental_status_id"));
        location.setProductID(rs.getInt("product_id"));
        location.setUserID(rs.getInt("user_id"));
        location.setCopyID(rs.getInt("copy_id"));
        location.setCreatedAt(rs.getTimestamp("createdAt").toLocalDateTime());
        location.setUpdatedAt(rs.getTimestamp("updatedAt").toLocalDateTime());

        return location;
    }
}
