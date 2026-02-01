package com.fitnesscamp.controller;

import com.fitnesscamp.entity.Camp;
import com.fitnesscamp.entity.Participant;
import com.fitnesscamp.entity.Participant.RegistrationStatus;
import com.fitnesscamp.service.CampService;
import com.fitnesscamp.service.ParticipantService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final CampService campService;
    private final ParticipantService participantService;

    // Dashboard
    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("totalCamps", campService.getTotalCampCount());
        model.addAttribute("activeCamps", campService.getActiveCampCount());
        model.addAttribute("totalParticipants", participantService.getTotalParticipantCount());
        model.addAttribute("pendingRegistrations", participantService.getPendingCount());
        model.addAttribute("confirmedRegistrations", participantService.getConfirmedCount());
        model.addAttribute("recentParticipants", participantService.getAllParticipants());
        model.addAttribute("camps", campService.getAllCamps());
        return "admin/dashboard";
    }

    // Camp Management
    @GetMapping("/camps")
    public String listCamps(Model model) {
        model.addAttribute("camps", campService.getAllCamps());
        return "admin/camps/list";
    }

    @GetMapping("/camps/new")
    public String newCampForm(Model model) {
        model.addAttribute("camp", new Camp());
        return "admin/camps/form";
    }

    @GetMapping("/camps/edit/{id}")
    public String editCampForm(@PathVariable Long id, Model model) {
        Camp camp = campService.getCampById(id)
                .orElseThrow(() -> new RuntimeException("Camp not found"));
        model.addAttribute("camp", camp);
        return "admin/camps/form";
    }

    @PostMapping("/camps/save")
    public String saveCamp(@Valid @ModelAttribute("camp") Camp camp,
                          BindingResult result,
                          RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "admin/camps/form";
        }
        campService.saveCamp(camp);
        redirectAttributes.addFlashAttribute("successMessage", "Camp saved successfully!");
        return "redirect:/admin/camps";
    }

    @PostMapping("/camps/delete/{id}")
    public String deleteCamp(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        campService.deleteCamp(id);
        redirectAttributes.addFlashAttribute("successMessage", "Camp deleted successfully!");
        return "redirect:/admin/camps";
    }

    @GetMapping("/camps/{id}/participants")
    public String campParticipants(@PathVariable Long id, Model model) {
        Camp camp = campService.getCampById(id)
                .orElseThrow(() -> new RuntimeException("Camp not found"));
        model.addAttribute("camp", camp);
        model.addAttribute("participants", participantService.getParticipantsByCamp(id));
        return "admin/camps/participants";
    }

    // Participant Management
    @GetMapping("/participants")
    public String listParticipants(@RequestParam(required = false) String search, Model model) {
        if (search != null && !search.isEmpty()) {
            model.addAttribute("participants", participantService.searchParticipants(search));
            model.addAttribute("searchTerm", search);
        } else {
            model.addAttribute("participants", participantService.getAllParticipants());
        }
        return "admin/participants/list";
    }

    @GetMapping("/participants/new")
    public String newParticipantForm(Model model) {
        model.addAttribute("participant", new Participant());
        model.addAttribute("camps", campService.getActiveCamps());
        model.addAttribute("statuses", RegistrationStatus.values());
        return "admin/participants/form";
    }

    @GetMapping("/participants/edit/{id}")
    public String editParticipantForm(@PathVariable Long id, Model model) {
        Participant participant = participantService.getParticipantById(id)
                .orElseThrow(() -> new RuntimeException("Participant not found"));
        model.addAttribute("participant", participant);
        model.addAttribute("camps", campService.getActiveCamps());
        model.addAttribute("statuses", RegistrationStatus.values());
        return "admin/participants/form";
    }

    @PostMapping("/participants/save")
    public String saveParticipant(@Valid @ModelAttribute("participant") Participant participant,
                                  BindingResult result,
                                  Model model,
                                  RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("camps", campService.getActiveCamps());
            model.addAttribute("statuses", RegistrationStatus.values());
            return "admin/participants/form";
        }
        
        Camp camp = campService.getCampById(participant.getCamp().getId())
                .orElseThrow(() -> new RuntimeException("Camp not found"));
        participant.setCamp(camp);
        
        participantService.saveParticipant(participant);
        redirectAttributes.addFlashAttribute("successMessage", "Participant saved successfully!");
        return "redirect:/admin/participants";
    }

    @PostMapping("/participants/delete/{id}")
    public String deleteParticipant(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        participantService.deleteParticipant(id);
        redirectAttributes.addFlashAttribute("successMessage", "Participant deleted successfully!");
        return "redirect:/admin/participants";
    }

    @PostMapping("/participants/{id}/status")
    public String updateParticipantStatus(@PathVariable Long id,
                                          @RequestParam RegistrationStatus status,
                                          RedirectAttributes redirectAttributes) {
        participantService.updateStatus(id, status);
        redirectAttributes.addFlashAttribute("successMessage", "Status updated successfully!");
        return "redirect:/admin/participants";
    }
}
