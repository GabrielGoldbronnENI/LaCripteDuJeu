package fr.eni.lacriptedujeu.controllers;

import fr.eni.lacriptedujeu.models.Copy;
import fr.eni.lacriptedujeu.models.Location;
import fr.eni.lacriptedujeu.models.Product;
import fr.eni.lacriptedujeu.services.*;
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
@RequestMapping("/locations")
public class LocationController {
    private static final Logger logger = LoggerFactory.getLogger(LocationController.class);

    private final LocationService locationService;
    private final UserService userService;
    private final CopyService copyService;
    private final ProductService productService;

    @Autowired
    public LocationController(LocationService locationService,UserService userService, CopyService copyService, ProductService productService) {
        this.locationService = locationService;
        this.userService = userService;
        this.copyService = copyService;
        this.productService = productService;
    }


    @PostMapping
    public String createLocation(@ModelAttribute @Valid Location location, BindingResult bindingResult, Model model) {
        model.addAttribute("users", userService.getAll(null));

        List<String> filtersCopyActive = new ArrayList<>();
        filtersCopyActive.add("");
        filtersCopyActive.add(String.valueOf(1));
        model.addAttribute("copies", copyService.getAll(filtersCopyActive));

        logger.info("Creating product: {}", location);

        if (bindingResult.hasErrors()) {
            logger.error("Validation errors while creating location: {}", bindingResult.getAllErrors());

            model.addAttribute("errors", bindingResult.getAllErrors());
            model.addAttribute("location", location);

            return "location-add";
        }

        try {
            locationService.save(location);
            logger.info("Product created successfully: {}", location);

        } catch (Exception e) {
            logger.error("Error occurred while saving location: {}", location, e);

            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("location", location);
            return "location-add";
        }

        return "redirect:/locations";
    }

    @GetMapping
    public String getAllLocations (
            @RequestParam(required = false) Integer copy,
            @RequestParam(required = false) Integer user,
            @RequestParam(required = false) Integer status,
            Model model
    ) {
        model.addAttribute("users", userService.getAll(null));
        model.addAttribute("copies", copyService.getAll(null));
        model.addAttribute("products", copyService.getAll(null));

        List<String> filter = new ArrayList<>();
        if (copy != null) {
            filter.add(String.valueOf(copy));
        } else {
            filter.add("");
        }
        if (user != null) {
            filter.add(String.valueOf(user));
        } else {
            filter.add("");
        }
        if (status != null) {
            filter.add(String.valueOf(status));
        } else {
            filter.add("");
        }

        logger.info("filter: {}", filter);

        model.addAttribute("locations", locationService.getAll(filter));
        return "locations";
    }

    @GetMapping("/{locationsID}")
    public String getLocation(@PathVariable int locationsID, Model model) {
        Location location = locationService.getById(locationsID);
        model.addAttribute("location", location);
        model.addAttribute("users", userService.getAll(null));
        List<String> filtersCopyActive = new ArrayList<>();
        filtersCopyActive.add("");
        filtersCopyActive.add(String.valueOf(1));

        List<Copy> copies = copyService.getAll(filtersCopyActive);
        copies.add(copyService.getById(location.getCopyID()));

        model.addAttribute("copies", copies);

        return "location";
    }


    @PostMapping("/update/{locationID}")
    public String update(@PathVariable int locationID, @ModelAttribute @Valid Location location, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResult.getAllErrors());
            return "location";
        }

        try {
            locationService.update(locationID, location);
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "location";
        }

        return "redirect:/locations";
    }

    @GetMapping("/delete/{locationID}")
    public String delete(@PathVariable int locationID) {
        locationService.delete(locationID);
        return "redirect:/locations";
    }
}