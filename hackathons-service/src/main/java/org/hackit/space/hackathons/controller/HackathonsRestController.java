package org.hackit.space.hackathons.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.hackit.space.hackathons.controller.payload.NewHackathonPayload;
import org.hackit.space.hackathons.entity.Hackathon;
import org.hackit.space.hackathons.service.HackathonService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("hackathons-api/hackathons")
public class HackathonsRestController {

    private final HackathonService hackathonService;

    @GetMapping
    public Iterable<Hackathon> findHackathons(@RequestParam(name = "filter", required = false) String filter) {
        return this.hackathonService.findAllHackathons(filter);
    }

    @PostMapping
    public ResponseEntity<?> createHackathon(@Valid @RequestBody NewHackathonPayload payload,
                                             BindingResult bindingResult,
                                             UriComponentsBuilder uriComponentsBuilder) throws BindException {
        if (bindingResult.hasErrors()) {
            if (bindingResult instanceof BindException exception) {
                throw exception;
            } else {
                throw new BindException(bindingResult);
            }
        } else {
            Hackathon hackathon = this.hackathonService.createHackathon(payload.title(), payload.description());
            return ResponseEntity
                    .created(uriComponentsBuilder
                            .replacePath("/hackathons-api/hackathons/{hackathonId}")
                            .build(Map.of("hackathonId", hackathon.getId())))
                    .body(hackathon);
        }
    }
}
