package fr.eni.lacriptedujeu.repositorys;

import fr.eni.lacriptedujeu.exceptions.LinkedException;
import fr.eni.lacriptedujeu.exceptions.NotFoundException;
import fr.eni.lacriptedujeu.models.Copy;
import fr.eni.lacriptedujeu.rowMapper.CopyRowMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Repository
public class CopyRepositoryImpl implements CopyRepository {
    private static final Logger logger = LoggerFactory.getLogger(CopyRepositoryImpl.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Transactional
    public void save(Copy copy) {
        String copySql = "INSERT INTO copy (barcode,status, product_id) VALUES (?, ?, ?) RETURNING copy_id";
        Integer copy_id = jdbcTemplate.queryForObject(copySql, Integer.class, copy.getBarcode(), copy.isStatus(), copy.getProductID());

        String productSql = "INSERT INTO product_copy(product_id, copy_id) VALUES (?, ?)";
        jdbcTemplate.update(productSql, copy.getProductID(), copy_id);
    }

    public List<Copy> getAll(List<String> filters) {
        StringBuilder sql = new StringBuilder("SELECT * FROM copy WHERE 1=1");
        List<Object> params = new ArrayList<>();

        String[] columns = {"product_id", "status"};

        logger.info("Getting all filters: {}", filters);
        if (filters != null && !filters.isEmpty()) {
            for (int i = 0; i < filters.size(); i++) {
                String filter = filters.get(i);
                if (filter != null && !filter.isEmpty()) {
                    if ("product_id".equals(columns[i])) {
                        sql.append(" AND ").append(columns[i]).append(" = ?");
                        params.add(Integer.parseInt(filter));
                    }else if ("status".equals(columns[i])) {
                        sql.append(" AND ").append(columns[i]).append(" = ?");
                        params.add(Boolean.parseBoolean(filter));
                    }
                }
            }
        }

        return jdbcTemplate.query(sql.toString(), new CopyRowMapper(jdbcTemplate), params.toArray());
    }


    public Copy getById(int copyID) {
        String sql = "SELECT * FROM copy WHERE copy_id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new CopyRowMapper(jdbcTemplate), copyID);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Transactional
    public void update(int copyID, Copy copy) {
        String copySql = """
                UPDATE copy 
                SET barcode = ?, status = ?, product_id = ? 
                WHERE copy_id = ?
                """;
        jdbcTemplate.update(copySql, copy.getBarcode(), copy.isStatus(), copy.getProductID(), copyID);
        logger.info("Copy updated with ID: {}", copyID);
    }

    public void delete(int copyID) {
        String checkQuery = """
                    SELECT COUNT(*) 
                    FROM locations l
                    WHERE l.copy_id = ? AND l.rental_status_id = 1
                """;
        Integer count = jdbcTemplate.queryForObject(checkQuery, Integer.class, copyID);

        if (count != null && count > 0) {
            throw new LinkedException("L'exemplaire ne peut pas être supprimée car elle est liée à une location active.");
        }

        String deleteQuery = "DELETE FROM copy WHERE copy_id = ?";
        int rowsAffected = jdbcTemplate.update(deleteQuery, copyID);

        if (rowsAffected == 0) {
            throw new NotFoundException("L'utilisateur avec l'ID " + copyID + " est introuvable.");
        }

        logger.info("Copy deleted with ID: {}", copyID);
    }
}
