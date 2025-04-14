package org.hackit.space.managerapp.service;

import org.hackit.space.managerapp.entity.Hackathon;

import java.util.List;
import java.util.Optional;

public interface HackathonService {
    List<Hackathon> findAllHackathons();

    Hackathon createHackathon(String title, String description);

    Optional<Hackathon> findHackathon(int hackathonId);

    void updateHackathon(Integer id, String title, String description);

    void deleteHackathon(Integer id);
}
