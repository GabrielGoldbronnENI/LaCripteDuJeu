package fr.eni.lacriptedujeu.rowMapper;

import fr.eni.lacriptedujeu.models.AgeLimit;
import fr.eni.lacriptedujeu.models.Genre;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GenreRowMapper implements RowMapper<Genre> {
    @Override
    public Genre mapRow(ResultSet rs, int rowNum) throws SQLException {
        Genre genre = new Genre();
        genre.setGenreID(rs.getInt("genre_id"));
        genre.setTitle(rs.getString("title"));
        return genre;
    }
}
