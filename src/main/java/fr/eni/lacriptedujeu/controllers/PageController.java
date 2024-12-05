package fr.eni.lacriptedujeu.controllers;

import fr.eni.lacriptedujeu.models.Product;
import fr.eni.lacriptedujeu.models.User;
import fr.eni.lacriptedujeu.services.AgeLimitService;
import fr.eni.lacriptedujeu.services.GenreService;
import fr.eni.lacriptedujeu.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    private final AgeLimitService ageLimitService;
    private final GenreService genreService;

    @Autowired
    public PageController(AgeLimitService ageLimitService, GenreService genreService) {
        this.ageLimitService = ageLimitService;
        this.genreService = genreService;
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


}
