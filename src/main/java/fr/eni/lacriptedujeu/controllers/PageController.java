package fr.eni.lacriptedujeu.controllers;

import fr.eni.lacriptedujeu.ColorfulLoggerExample;
import fr.eni.lacriptedujeu.models.Copy;
import fr.eni.lacriptedujeu.models.Location;
import fr.eni.lacriptedujeu.models.Product;
import fr.eni.lacriptedujeu.models.User;
import fr.eni.lacriptedujeu.services.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class PageController {
    private static final Logger logger = LoggerFactory.getLogger(ColorfulLoggerExample.class);

    private final AgeLimitService ageLimitService;
    private final GenreService genreService;
    private final ProductService productService;
    private final UserService userService;
    private final CopyService copyService;

    @Autowired
    public PageController(AgeLimitService ageLimitService, GenreService genreService, ProductService productService, UserService userService, CopyService copyService) {
        this.ageLimitService = ageLimitService;
        this.genreService = genreService;
        this.productService = productService;
        this.userService = userService;
        this.copyService = copyService;
    }

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/user-add")
    public String newUser(Model model) {
        model.addAttribute("user", new User());
        return "user-add";
    }

    @GetMapping("/product-add")
    public String newProduct(Model model) {
        model.addAttribute("ageLimits", ageLimitService.getAll());
        model.addAttribute("genres", genreService.getAll());
        model.addAttribute("product", new Product());
        return "product-add";
    }


    @GetMapping("/copy-add")
    public String newCopy(Model model) {
        model.addAttribute("products", productService.getAll(null, 0, 9000));
        model.addAttribute("copy", new Copy());
        return "copy-add";
    }

    @GetMapping("/location-add")
    public String newLocation(Model model) {
        model.addAttribute("users", userService.getAll(null, 0, 9000));
        model.addAttribute("copies", copyService.getAllAvailable());

        model.addAttribute("location", new Location());
        return "location-add";
    }


}
