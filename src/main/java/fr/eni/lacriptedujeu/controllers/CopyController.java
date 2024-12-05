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
    public String createCopy(@ModelAttribute @Valid Copy copy, BindingResult bindingResult, Model model) {
        model.addAttribute("products", productService.getAll(null));
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
            @RequestParam(required = false) Boolean status,
            @RequestParam(required = false) Integer productID,
            Model model
    ) {
        logger.info("Getting all copies with filters - status: {}, productID: {}", status, productID);

        List<String> filters = new ArrayList<>();
        if (status != null) {
            filters.add(String.valueOf(status));
        }
        if (productID != null) {
            filters.add(String.valueOf(productID));
        }

        model.addAttribute("copies", copyService.getAll(filters));
        return "copies";
    }

    @GetMapping("/{copyID}")
    public String getCopy(@PathVariable int copyID, Model model) {
        model.addAttribute("products", productService.getAll(null));
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
