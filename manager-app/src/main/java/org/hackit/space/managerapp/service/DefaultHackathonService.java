package org.hackit.space.managerapp.service;

import lombok.RequiredArgsConstructor;
import org.hackit.space.managerapp.entity.Hackathon;
import org.hackit.space.managerapp.repository.HackathonRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DefaultHackathonService implements HackathonService {

    private final HackathonRepository hackathonRepository;

    @Override
    public List<Hackathon> findAllHackathons() {
        return hackathonRepository.findAll();
    }

    @Override
    public Hackathon createHackathon(String title, String description) {
        return this.hackathonRepository.save(new Hackathon(null, title, description));
    }

    @Override
    public Optional<Hackathon> findHackathon(int hackathonId) {
        return this.hackathonRepository.findById(hackathonId);
    }

    @Override
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
    public void deleteHackathon(Integer id) {
        this.hackathonRepository.deleteById(id);
    }
}
