package com.fitnesscamp.controller;

import com.fitnesscamp.entity.Camp;
import com.fitnesscamp.entity.Participant;
import com.fitnesscamp.service.CampService;
import com.fitnesscamp.service.ParticipantService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class RegistrationController {

    private final CampService campService;
    private final ParticipantService participantService;

    @GetMapping("/camps")
    public String listCamps(Model model) {
        model.addAttribute("camps", campService.getUpcomingCamps());
        return "camps/list";
    }

    @GetMapping("/camps/view/{id}")
    public String viewCamp(@PathVariable Long id, Model model) {
        Camp camp = campService.getCampById(id)
                .orElseThrow(() -> new RuntimeException("Camp not found"));
        model.addAttribute("camp", camp);
        return "camps/view";
    }

    @GetMapping("/register")
    public String showRegistrationForm(@RequestParam(required = false) Long campId, Model model) {
        Participant participant = new Participant();
        List<Camp> camps = campService.getUpcomingCamps();
        
        if (campId != null) {
            Camp selectedCamp = campService.getCampById(campId).orElse(null);
            if (selectedCamp != null) {
                participant.setCamp(selectedCamp);
            }
        }
        
        model.addAttribute("participant", participant);
        model.addAttribute("camps", camps);
        return "registration/form";
    }

    @PostMapping("/register")
    public String processRegistration(@Valid @ModelAttribute("participant") Participant participant,
                                      BindingResult result,
                                      Model model,
                                      RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("camps", campService.getUpcomingCamps());
            return "registration/form";
        }

        Camp selectedCamp = campService.getCampById(participant.getCamp().getId())
                .orElseThrow(() -> new RuntimeException("Camp not found"));
        
        if (!selectedCamp.hasAvailableSpots()) {
            result.rejectValue("camp", "error.camp", "This camp is full. Please select another camp.");
            model.addAttribute("camps", campService.getUpcomingCamps());
            return "registration/form";
        }

        participant.setCamp(selectedCamp);
        participantService.registerParticipant(participant);
        
        redirectAttributes.addFlashAttribute("successMessage", 
            "Registration successful! You will receive a confirmation email shortly.");
        return "redirect:/registration/success";
    }

    @GetMapping("/registration/success")
    public String registrationSuccess() {
        return "registration/success";
    }
}
