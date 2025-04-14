package org.hackit.space.managerapp.repository;

import org.hackit.space.managerapp.entity.Hackathon;

import java.util.List;
import java.util.Optional;

public interface HackathonRepository {
    List<Hackathon> findAll();

    Hackathon save(Hackathon hackathon);

    Optional<Hackathon> findById(int hackathonId);

    void deleteById(Integer id);
}
