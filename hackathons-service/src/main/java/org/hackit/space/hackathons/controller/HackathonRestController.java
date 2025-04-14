package org.hackit.space.hackathons.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.hackit.space.hackathons.controller.payload.UpdateHackathonPayload;
import org.hackit.space.hackathons.entity.Hackathon;
import org.hackit.space.hackathons.service.HackathonService;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;
import java.util.NoSuchElementException;

@RestController
@RequiredArgsConstructor
@RequestMapping("hackathons-api/hackathons/{hackathonId:\\d+}")
public class HackathonRestController {

    private final HackathonService hackathonService;

    private final MessageSource messageSource;

    @ModelAttribute("hackathon")
    public Hackathon getHackathon(@PathVariable("hackathonId") int hackathonId) {
        return this.hackathonService.findHackathon(hackathonId)
                .orElseThrow(() -> new NoSuchElementException("errors.hackathon.not_found"));
    }

    @GetMapping
    public Hackathon findHackathon(@ModelAttribute("hackathon") Hackathon hackathon) {
        return hackathon;
    }

    @PatchMapping
    public ResponseEntity<?> updateHackathon(@PathVariable("hackathonId") int hackathonId,
                                             @Valid @RequestBody UpdateHackathonPayload payload,
                                             BindingResult bindingResult) throws BindException {
        if (bindingResult.hasErrors()) {
            if (bindingResult instanceof BindException exception) {
                throw exception;
            } else {
                throw new BindException(bindingResult);
            }
        } else {
            this.hackathonService.updateHackathon(hackathonId, payload.title(), payload.description());
            return ResponseEntity.noContent()
                    .build();
        }
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteHackathon(@PathVariable("hackathonId") int hackathonId) {
        this.hackathonService.deleteHackathon(hackathonId);
        return ResponseEntity.noContent()
                .build();
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ProblemDetail> handleNoSuchElementException(NoSuchElementException exception,
                                                                      Locale locale) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND,
                        this.messageSource.getMessage(exception.getMessage(), new Object[0],
                                exception.getMessage(), locale)));
    }
}
