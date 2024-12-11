package fr.eni.lacriptedujeu.controllers;

import fr.eni.lacriptedujeu.models.User;
import fr.eni.lacriptedujeu.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public String createUser(@ModelAttribute @Valid User user, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResult.getAllErrors());
            model.addAttribute("user", user);
            return "user-add";
        }

        try {
            userService.save(user);
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("user", user);
            return "user-add";
        }

        return "redirect:/users";
    }

    @GetMapping
    public String getAllUsers(
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String lastName,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String phone,
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "50") int size,
            Model model
    ) {
        List<String> filter = new ArrayList<>();

        filter.add(firstName);
        filter.add(lastName);
        filter.add(email);
        filter.add(phone);

        model.addAttribute("users", userService.getAll(filter, page, size));
        return "users";
    }

    @GetMapping("/{userID}")
    public String getUser(@PathVariable int userID, Model model) {
        model.addAttribute("user", userService.getById(userID));
        return "user";
    }


    @PostMapping("/update/{userID}")
    public String update(@PathVariable int userID, @ModelAttribute @Valid User user, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResult.getAllErrors());
            return "user";
        }

        try {
            userService.update(userID, user);
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "user";
        }

        return "redirect:/users";
    }

    @GetMapping("/delete/{userID}")
    public String delete(@PathVariable int userID) {
        userService.delete(userID);
        return "redirect:/users";
    }
}