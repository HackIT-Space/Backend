package org.hackit.space.hackathons.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.hackit.space.hackathons.entity.Hackathon;
import org.hackit.space.hackathons.repository.HackathonRepository;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DefaultHackathonService implements HackathonService {

    private final HackathonRepository hackathonRepository;

    @Override
    public Iterable<Hackathon> findAllHackathons(String filter) {
        if (filter != null && !filter.isBlank()) {
            return hackathonRepository.findAllByTitleLikeIgnoreCase("%" + filter + "%");
        } else {
            return hackathonRepository.findAll();
        }
    }

    @Override
    @Transactional
    public Hackathon createHackathon(String title, String description) {
        return this.hackathonRepository.save(new Hackathon(null, title, description));
    }

    @Override
    public Optional<Hackathon> findHackathon(int hackathonId) {
        return this.hackathonRepository.findById(hackathonId);
    }

    @Override
    @Transactional
    public void updateHackathon(Integer id, String title, String description) {
        this.hackathonRepository.findById(id)
                .ifPresentOrElse(hackathon -> {
                    hackathon.setTitle(title);
                    hackathon.setDescription(description);
                }, () -> {
                    throw new NoSuchElementException();
                });
    }

    @Override
    @Transactional
    public void deleteHackathon(Integer id) {
        this.hackathonRepository.deleteById(id);
    }
}
