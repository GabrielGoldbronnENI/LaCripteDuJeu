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
@RequestMapping("/products")
public class ProductController {
    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    private final ProductService productService;
    private final AgeLimitService ageLimitService;
    private final GenreService genreService;

    @Autowired
    public ProductController(ProductService productService, AgeLimitService ageLimitService, GenreService genreService) {
        this.productService = productService;
        this.ageLimitService = ageLimitService;
        this.genreService = genreService;
    }


    @PostMapping
    public String createProduct(@ModelAttribute @Valid Product product, BindingResult bindingResult, Model model) {
        model.addAttribute("ageLimits", ageLimitService.getAll());
        model.addAttribute("genres", genreService.getAll());

        logger.info("Creating product: {}", product);

        if (bindingResult.hasErrors()) {
            logger.error("Validation errors while creating product: {}", bindingResult.getAllErrors());

            model.addAttribute("errors", bindingResult.getAllErrors());
            model.addAttribute("product", product);

            return "product-add";
        }

        try {
            productService.save(product);
            logger.info("Product created successfully: {}", product);

        } catch (Exception e) {
            logger.error("Error occurred while saving product: {}", product, e);

            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("product", product);
            return "product-add";
        }

        return "redirect:/products";
    }

    @GetMapping
    public String getAllProducts (
            @RequestParam(required = false) String title,
            @RequestParam(required = false) Integer ageLimit,
            Model model
    ) {
        model.addAttribute("ageLimits", ageLimitService.getAll());

        System.out.println("getAllProducts ageLimit : " + ageLimit);
        model.addAttribute("title", title);

        List<String> filter = new ArrayList<>();
        filter.add(title);
        if (ageLimit != null) {
            filter.add(String.valueOf(ageLimit));
        } else {
            filter.add("");
        }

        model.addAttribute("products", productService.getAll(filter));
        return "products";
    }

    @GetMapping("/{productID}")
    public String getProduct(@PathVariable int productID, Model model) {
        model.addAttribute("product", productService.getById(productID));
        model.addAttribute("ageLimits", ageLimitService.getAll());
        model.addAttribute("genres", genreService.getAll());
        return "product";
    }


    @PostMapping("/update/{productID}")
    public String update(@PathVariable int productID, @ModelAttribute @Valid Product product, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResult.getAllErrors());
            return "product";
        }

        try {
            productService.update(productID, product);
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "product";
        }

        return "redirect:/products";
    }

    @GetMapping("/delete/{productID}")
    public String delete(@PathVariable int productID) {
        productService.delete(productID);
        return "redirect:/products";
    }
}