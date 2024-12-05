package fr.eni.lacriptedujeu.repositorys;

import fr.eni.lacriptedujeu.models.AgeLimit;
import fr.eni.lacriptedujeu.models.Genre;
import fr.eni.lacriptedujeu.rowMapper.AgeLimitRowMapper;
import fr.eni.lacriptedujeu.rowMapper.GenreRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class GenreRepositoryImpl implements GenreRepository {
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public List<Genre> getAll() {
        String sql = "SELECT * FROM genre";
        return jdbcTemplate.query(sql, new GenreRowMapper());
    }
}
