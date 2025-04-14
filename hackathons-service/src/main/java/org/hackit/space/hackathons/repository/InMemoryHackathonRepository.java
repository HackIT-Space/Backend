package org.hackit.space.hackathons.repository;

import org.hackit.space.hackathons.entity.Hackathon;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class InMemoryHackathonRepository implements HackathonRepository {

    private final List<Hackathon> hackathons = Collections.synchronizedList(new LinkedList<>());

    @Override
    public List<Hackathon> findAll() {
        return Collections.unmodifiableList(hackathons);
    }

    @Override
    public Hackathon save(Hackathon hackathon) {
        hackathon.setId(this.hackathons.stream().max(Comparator.comparingInt(Hackathon::getId))
                .map(Hackathon::getId)
                .orElse(0) + 1);
        this.hackathons.add(hackathon);
        return hackathon;
    }

    @Override
    public Optional<Hackathon> findById(int hackathonId) {
        return this.hackathons.stream()
                .filter(hackathon -> Objects.equals(hackathonId, hackathon.getId()))
                .findFirst();
    }

    @Override
    public void deleteById(Integer id) {
        this.hackathons.removeIf(hackathon -> Objects.equals(id, hackathon.getId()));
    }
}
