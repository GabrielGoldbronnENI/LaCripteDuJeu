package fr.eni.lacriptedujeu.controllers;

import fr.eni.lacriptedujeu.models.Product;
import fr.eni.lacriptedujeu.services.AgeLimitService;
import fr.eni.lacriptedujeu.services.GenreService;
import fr.eni.lacriptedujeu.services.ProductService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/genres")
public class GenresController {
    private static final Logger logger = LoggerFactory.getLogger(GenresController.class);

    private final ProductService productService;
    private final AgeLimitService ageLimitService;
    private final GenreService genreService;

    @Autowired
    public GenresController(ProductService productService, AgeLimitService ageLimitService, GenreService genreService) {
        this.productService = productService;
        this.ageLimitService = ageLimitService;
        this.genreService = genreService;
    }

    @GetMapping
    public String getAllGenres (Model model) {
        logger.info("getAllGenres : {}", genreService.getAll());
        return "index";
    }

    @GetMapping("/{genreID}")
    public String getProduct(@PathVariable int genreID, Model model) {
        logger.info("getAllGenres : {}", genreService.getById(genreID));
        return "index";
    }
}