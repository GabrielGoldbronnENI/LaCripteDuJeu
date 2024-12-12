package fr.eni.lacriptedujeu.controllers;

import fr.eni.lacriptedujeu.models.Copy;
import fr.eni.lacriptedujeu.services.CopyService;
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
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/copies")
public class CopyController {
    private static final Logger logger = LoggerFactory.getLogger(CopyController.class);

    private final CopyService copyService;
    private final ProductService productService;

    @Autowired
    public CopyController(CopyService copyService, ProductService productService) {
        this.copyService = copyService;
        this.productService = productService;
    }

    @PostMapping
    public String createCopy(
            @ModelAttribute @Valid Copy copy,
            BindingResult bindingResult,
            Model model) {
        model.addAttribute("products", productService.getAll(null, 0, 9000));
        logger.info("Creating copy: {}", copy);

        if (bindingResult.hasErrors()) {
            logger.error("Validation errors while creating copy: {}", bindingResult.getAllErrors());

            model.addAttribute("errors", bindingResult.getAllErrors());
            model.addAttribute("copy", copy);

            return "copy-add";
        }

        try {
            copyService.save(copy);
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("copy", copy);
            return "copy-add";
        }

        logger.info("Copy created successfully: {}", copy);

        return "redirect:/copies";
    }

    @GetMapping
    public String getAllCopies(
            @RequestParam Map<String, String> params,
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "50") int size,
            Model model
    ) {
        model.addAttribute("products", productService.getAll(null, 0, 9000));
        logger.info("Getting all copies with filters - params: {}", params);

        List<String> filters = new ArrayList<>();
        filters.add(params.getOrDefault("productID", ""));
        filters.add(params.getOrDefault("status", ""));


        List<Copy> copies = copyService.getAll(filters, page, size);
        int totalItems = copyService.getTotalCount(filters);
        int totalPages = (int) Math.ceil((double) totalItems / size);


        model.addAttribute("copies", copies);
        model.addAttribute("page", page);
        model.addAttribute("size", size);
        model.addAttribute("totalPages", totalPages);

        String filtersQueryString = params.entrySet().stream()
                .filter(entry -> !entry.getKey().equals("page") && !entry.getKey().equals("size"))
                .filter(entry -> entry.getValue() != null && !entry.getValue().isEmpty()) // Exclure les valeurs nulles ou vides
                .map(entry -> entry.getKey() + "=" + entry.getValue())
                .collect(Collectors.joining("&"));

        model.addAttribute("filters", filtersQueryString);
        return "copies";
    }



    @GetMapping("/{copyID}")
    public String getCopy(@PathVariable int copyID, Model model) {
        model.addAttribute("products", productService.getAll(null, 0, 9000));
        logger.info("Getting copy with ID: {}", copyID);

        Copy copy = copyService.getById(copyID);
        model.addAttribute("copy", copy);

        return "copy";
    }

    @PostMapping("/update/{copyID}")
    public String updateCopy(@PathVariable int copyID, @ModelAttribute @Valid Copy copy, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            logger.error("Validation errors while updating copy: {}", bindingResult.getAllErrors());
            model.addAttribute("errors", bindingResult.getAllErrors());
            return "copy";
        }

        copyService.update(copyID, copy);
        logger.info("Copy updated successfully: {}", copy);

        return "redirect:/copies";
    }

    @GetMapping("/delete/{copyID}")
    public String deleteCopy(@PathVariable int copyID) {
        logger.info("Deleting copy with ID: {}", copyID);
        copyService.delete(copyID);
        logger.info("Copy deleted successfully: {}", copyID);

        return "redirect:/copies";
    }
}
