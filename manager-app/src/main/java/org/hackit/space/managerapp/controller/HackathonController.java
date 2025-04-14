package org.hackit.space.managerapp.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.hackit.space.managerapp.client.BadRequestException;
import org.hackit.space.managerapp.client.HackathonsRestClient;
import org.hackit.space.managerapp.controller.entity.Hackathon;
import org.hackit.space.managerapp.controller.payload.UpdateHackathonPayload;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;
import java.util.NoSuchElementException;

@Controller
@RequiredArgsConstructor
@RequestMapping("hackathons/{hackathonId:\\d+}")
public class HackathonController {

    private final HackathonsRestClient hackathonsRestClient;

    private final MessageSource messageSource;

    @ModelAttribute("hackathon")
    public Hackathon hackathon(@PathVariable("hackathonId") int hackathonId) {
        return this.hackathonsRestClient.findHackathon(hackathonId)
                .orElseThrow(() -> new NoSuchElementException("errors.hackathon.not_found"));
    }

    @GetMapping
    public String getHackathon() {
        return "hackathons/hackathon";
    }

    @GetMapping("edit")
    public String getHackathonEditPage() {
        return "hackathons/edit";
    }

    @PostMapping("edit")
    public String updateHackathon(@ModelAttribute(value = "hackathon", binding = false) Hackathon hackathon,
                                  UpdateHackathonPayload payload,
                                  Model model) {
        try {
            this.hackathonsRestClient.updateHackathon(hackathon.id(), payload.title(), payload.description());
            return "redirect:/hackathons/%d".formatted(hackathon.id());
        } catch (BadRequestException exception) {
            model.addAttribute("payload", payload);
            model.addAttribute("errors", exception.getErrors());
            return "hackathons/edit";
        }
    }

    @PostMapping("delete")
    public String deleteHackathon(@ModelAttribute("hackathon") Hackathon hackathon) {
        this.hackathonsRestClient.deleteHackathon(hackathon.id());
        return "redirect:/hackathons/list";
    }

    @ExceptionHandler(NoSuchElementException.class)
    public String handleNoSuchElementException(NoSuchElementException exception,
                                               Model model,
                                               HttpServletResponse response,
                                               Locale locale) {
        response.setStatus(HttpStatus.NOT_FOUND.value());
        model.addAttribute("error",
                this.messageSource.getMessage(exception.getMessage(),
                        new Object[0],
                        exception.getMessage(),
                        locale));
        return "errors/404";
    }
}
