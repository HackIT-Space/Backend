package org.hackit.space.managerapp.client;


import org.hackit.space.managerapp.controller.entity.Hackathon;

import java.util.List;
import java.util.Optional;

public interface HackathonsRestClient {

    List<Hackathon> findAllHackathons();

    Hackathon createHackathon(String title, String description);

    Optional<Hackathon> findHackathon(int hackathonId);

    void updateHackathon(int hackathonId, String title, String description);

    void deleteHackathon(int hackathonId);
}
