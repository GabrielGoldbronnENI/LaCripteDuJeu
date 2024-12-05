package fr.eni.lacriptedujeu.rowMapper;

import fr.eni.lacriptedujeu.models.AgeLimit;
import fr.eni.lacriptedujeu.models.Genre;
import fr.eni.lacriptedujeu.models.Product;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class AgeLimitRowMapper implements RowMapper<AgeLimit> {
    @Override
    public AgeLimit mapRow(ResultSet rs, int rowNum) throws SQLException {
        AgeLimit ageLimit = new AgeLimit();
        ageLimit.setAgeLimitID(rs.getInt("age_limit_id"));
        ageLimit.setLabel(rs.getString("label"));
        return ageLimit;
    }
}
