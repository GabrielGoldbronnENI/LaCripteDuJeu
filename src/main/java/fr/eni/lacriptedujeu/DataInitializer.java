package fr.eni.lacriptedujeu;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

@Component
public class DataInitializer {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @PostConstruct
    public void init() {
        System.out.println("App started: Generating products...");
        generateCopies();
    }


    private void generateCopies() {
        Random random = new Random();

        long initialBarcode = 1515151515250L;
        int productCount = 10000;

        StringBuilder sql = new StringBuilder();
        sql.append("INSERT INTO copy (barcode, status, product_id) VALUES ");
        for (int i = 0; i < productCount; i++) {
            sql.append("('")
                    .append(initialBarcode + i)
                    .append("', ")
                    .append(random.nextBoolean())
                    .append(", ")
                    .append(random.nextInt(5) + 1)
                    .append(")");
            if (i < productCount - 1) {
                sql.append(", ");
            }
        }
        sql.append(";");

        System.out.println("Generated SQL: " + sql); // Ajout d'un log pour debug
        jdbcTemplate.update(sql.toString());
        System.out.println("Products generated!");
    }
}
