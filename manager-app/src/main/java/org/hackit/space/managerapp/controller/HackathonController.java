package org.hackit.space.managerapp.controller;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.hackit.space.managerapp.controller.payload.UpdateHackathonPayload;
import org.hackit.space.managerapp.entity.Hackathon;
import org.hackit.space.managerapp.service.HackathonService;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;
import java.util.NoSuchElementException;

@Controller
@RequiredArgsConstructor
@RequestMapping("hackathons/{hackathonId:\\d+}")
public class HackathonController {

    private final HackathonService hackathonService;

    private final MessageSource messageSource;

    @ModelAttribute("hackathon")
    public Hackathon hackathon(@PathVariable("hackathonId") int hackathonId) {
        return this.hackathonService.findHackathon(hackathonId)
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
                                  @Valid UpdateHackathonPayload payload,
                                  BindingResult bindingResult,
                                  Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("payload", payload);
            model.addAttribute("errors", bindingResult.getAllErrors().stream()
                    .map(ObjectError::getDefaultMessage)
                    .toList());
            return "hackathons/edit";
        } else {
            this.hackathonService.updateHackathon(hackathon.getId(), payload.title(), payload.description());
            return "redirect:/hackathons/%d".formatted(hackathon.getId());
        }
    }

    @PostMapping("delete")
    public String deleteHackathon(@ModelAttribute("hackathon") Hackathon hackathon) {
        this.hackathonService.deleteHackathon(hackathon.getId());
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
