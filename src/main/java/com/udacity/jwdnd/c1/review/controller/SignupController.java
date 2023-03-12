package com.udacity.jwdnd.c1.review.controller;

import com.udacity.jwdnd.c1.review.model.AppUser;
import com.udacity.jwdnd.c1.review.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller()
@RequestMapping("/signup")
public class SignupController {

    private final UserService userService;

    public SignupController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public String signupView() {
        return "signup";
    }

    @PostMapping()
    public String signupUser(@ModelAttribute AppUser user, Model model, RedirectAttributes redirectAttributes) {
        String signupError = null;
        boolean signUpSuccessful = false;


        if (!userService.isUsernameAvailable(user.getUsername())) {
            signupError = "The username already exists.";
        }

        if (signupError == null) {
            int rowsAdded = userService.createUser(user);
            if (rowsAdded < 0) {
                signupError = "There was an error signing you up. Please try again.";
            }
        }

        if (signupError == null) {
            model.addAttribute("signupSuccess", true);
            signUpSuccessful = true;
        } else {
            model.addAttribute("signupError", signupError);
        }

        if (signUpSuccessful) {
            // add flash attribute here
            redirectAttributes.addFlashAttribute("isSuccess", true);
            return "redirect:/login";
        } else {
            return "signup";
        }
    }
}
