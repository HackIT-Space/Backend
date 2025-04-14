package org.hackit.space.managerapp.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.hackit.space.managerapp.controller.payload.NewHackathonPayload;
import org.hackit.space.managerapp.entity.Hackathon;
import org.hackit.space.managerapp.service.HackathonService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("hackathons")
public class HackathonsController {

    private final HackathonService hackathonService;

    @GetMapping("list")
    public String getHackathonsList(Model model) {
        model.addAttribute("hackathons", this.hackathonService.findAllHackathons());
        return "hackathons/list";
    }

    @GetMapping("create")
    public String getNewHackathonPage() {
        return "hackathons/new_hackathon";
    }

    @PostMapping("create")
    public String createHackathon(@Valid NewHackathonPayload payload,
                                  BindingResult bindingResult,
                                  Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("payload", payload);
            model.addAttribute("errors", bindingResult.getAllErrors().stream()
                    .map(ObjectError::getDefaultMessage)
                    .toList());
            return "hackathons/new_hackathon";
        } else {
            Hackathon hackathon = hackathonService.createHackathon(payload.title(), payload.description());
            return "redirect:/hackathons/%d".formatted(hackathon.getId());
        }
    }
}
