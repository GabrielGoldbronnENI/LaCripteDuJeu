package fr.eni.lacriptedujeu.repositorys;

import fr.eni.lacriptedujeu.models.AgeLimit;
import fr.eni.lacriptedujeu.rowMapper.AgeLimitRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AgeLimitRepositoryImpl implements AgeLimitRepository {
    @Autowired
    JdbcTemplate jdbcTemplate;

    public List<AgeLimit> getAll() {
        String sql = "SELECT * FROM age_limit";
        return jdbcTemplate.query(sql, new AgeLimitRowMapper());
    }

    public AgeLimit getById(int ageLimitID) {
        String sql = "SELECT * FROM age_limit WHERE age_limit_id=?";
        try {
            return jdbcTemplate.queryForObject(sql, new AgeLimitRowMapper(), ageLimitID);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
}
