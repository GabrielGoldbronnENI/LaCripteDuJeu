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
import java.util.Map;
import java.util.stream.Collectors;

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
        model.addAttribute("users", userService.getAll(null, 0,9000));
        model.addAttribute("copies", copyService.getAllAvailable());

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
    public String getAllLocations(
            @RequestParam Map<String, String> params, // Capture all dynamic query parameters
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int size,
            Model model
    ) {
        model.addAttribute("users", userService.getAll(null, 0, 9000));
        model.addAttribute("copies", copyService.getAll(null, 0, 9000));
        model.addAttribute("products", copyService.getAll(null, 0, 9000));

        // Build filters dynamically
        List<String> filter = new ArrayList<>();
        if (params.containsKey("copy") && params.get("copy") != null && !params.get("copy").isEmpty()) {
            filter.add(params.get("copy"));
        } else {
            filter.add("");
        }

        if (params.containsKey("user") && params.get("user") != null && !params.get("user").isEmpty()) {
            filter.add(params.get("user"));
        } else {
            filter.add("");
        }

        if (params.containsKey("status") && params.get("status") != null && !params.get("status").isEmpty()) {
            filter.add(params.get("status"));
        } else {
            filter.add("");
        }

        logger.info("Filters: {}", filter);


        String filtersQueryString = params.entrySet().stream()
                .filter(entry -> !entry.getKey().equals("page") && !entry.getKey().equals("size"))
                .map(entry -> entry.getKey() + "=" + entry.getValue())
                .collect(Collectors.joining("&"));


        List<Location> locations = locationService.getAll(filter, page, size);
        int totalItems = locationService.getTotalCount(filter);
        int totalPages = (int) Math.ceil((double) totalItems / size);


        model.addAttribute("locations", locations);
        model.addAttribute("page", page);
        model.addAttribute("size", size);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("filters", filtersQueryString);

        return "locations";
    }

    @GetMapping("/{locationsID}")
    public String getLocation(@PathVariable int locationsID, Model model) {
        Location location = locationService.getById(locationsID);
        model.addAttribute("location", location);
        model.addAttribute("users", userService.getAll(null, 0,9000));
        List<Copy> copies = copyService.getAllAvailable();
        copies.add(copyService.getById(location.getCopy().getCopyID()));


        model.addAttribute("copies", copies);

        return "location";
    }


    @PostMapping("/update/{locationID}")
    public String update(@PathVariable int locationID, @ModelAttribute @Valid Location location, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResult.getAllErrors());
            return "location";
        }

        logger.info("updating product: {}", location);

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