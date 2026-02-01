package com.fitnesscamp.controller;

import com.fitnesscamp.service.CampService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final CampService campService;

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("upcomingCamps", campService.getUpcomingCamps());
        return "home";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/access-denied")
    public String accessDenied() {
        return "access-denied";
    }
}
