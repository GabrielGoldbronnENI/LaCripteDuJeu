package fr.eni.lacriptedujeu.repositorys;

import fr.eni.lacriptedujeu.models.AgeLimit;
import fr.eni.lacriptedujeu.rowMapper.AgeLimitRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AgeLimitRepositoryImpl implements AgeLimitRepository {
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public List<AgeLimit> getAll() {
        String sql = "SELECT * FROM age_limit";
        return jdbcTemplate.query(sql, new AgeLimitRowMapper());
    }
}
