package fr.eni.lacriptedujeu.repositorys;

import fr.eni.lacriptedujeu.models.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.junit.jupiter.api.Assertions.*;

@JdbcTest
class UserRepositoryImplTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    void save_shouldInsertUser() {
        // Arrange
        String firstName = "John";
        String lastName = "Doe";
        String email = "john.doe@example.com";
        String phone = "1234567890";
        String address = "123 Street, City";

        // Act
        String sql = "INSERT INTO users (first_name, last_name, email, phone, address) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, firstName, lastName, email, phone, address);

        // Assert
        String countSql = "SELECT COUNT(*) FROM users WHERE email = ?";
        Integer count = jdbcTemplate.queryForObject(countSql, Integer.class, email);
        assertNotNull(count);
        assertEquals(1, count);
    }
}
