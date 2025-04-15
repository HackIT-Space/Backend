package org.hackit.space.managerapp.controller;

import lombok.RequiredArgsConstructor;
import org.hackit.space.managerapp.client.BadRequestException;
import org.hackit.space.managerapp.client.HackathonsRestClient;
import org.hackit.space.managerapp.controller.entity.Hackathon;
import org.hackit.space.managerapp.controller.payload.NewHackathonPayload;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@RequestMapping("hackathons")
public class HackathonsController {

    private final HackathonsRestClient hackathonsRestClient;

    @GetMapping("list")
    public String getHackathonsList(Model model, @RequestParam(name = "filter", required = false) String filter) {
        model.addAttribute("hackathons", this.hackathonsRestClient.findAllHackathons(filter));
        model.addAttribute("filter", filter);
        return "hackathons/list";
    }

    @GetMapping("create")
    public String getNewHackathonPage() {
        return "hackathons/new_hackathon";
    }

    @PostMapping("create")
    public String createHackathon(NewHackathonPayload payload,
                                  Model model) {
        try {
            Hackathon hackathon = hackathonsRestClient.createHackathon(payload.title(), payload.description());
            return "redirect:/hackathons/%d".formatted(hackathon.id());
        } catch (BadRequestException exception) {
            model.addAttribute("payload", payload);
            model.addAttribute("errors", exception.getErrors());
            return "hackathons/new_hackathon";
        }
    }
}
