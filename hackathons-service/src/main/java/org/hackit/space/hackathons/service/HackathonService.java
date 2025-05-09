package org.hackit.space.hackathons.service;


import org.hackit.space.hackathons.entity.Hackathon;

import java.util.Optional;

public interface HackathonService {

    Iterable<Hackathon> findAllHackathons(String filter);

    Hackathon createHackathon(String title, String description);

    Optional<Hackathon> findHackathon(int hackathonId);

    void updateHackathon(Integer id, String title, String description);

    void deleteHackathon(Integer id);
}
