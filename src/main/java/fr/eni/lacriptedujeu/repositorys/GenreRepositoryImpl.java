package fr.eni.lacriptedujeu.repositorys;

import fr.eni.lacriptedujeu.exceptions.NotFoundException;
import fr.eni.lacriptedujeu.models.AgeLimit;
import fr.eni.lacriptedujeu.models.Genre;
import fr.eni.lacriptedujeu.rowMapper.AgeLimitRowMapper;
import fr.eni.lacriptedujeu.rowMapper.GenreRowMapper;
import fr.eni.lacriptedujeu.rowMapper.ProductRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class GenreRepositoryImpl implements GenreRepository {
    @Autowired
    JdbcTemplate jdbcTemplate;

    public List<Genre> getAll() {
        String sql = "SELECT * FROM genre";
        return jdbcTemplate.query(sql, new GenreRowMapper());
    }

    public Genre getById(int genreID) {
        String sql = "SELECT * FROM genre WHERE genre_id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new GenreRowMapper(), genreID);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

}
