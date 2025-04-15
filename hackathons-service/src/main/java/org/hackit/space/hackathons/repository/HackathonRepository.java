package org.hackit.space.hackathons.repository;


import org.hackit.space.hackathons.entity.Hackathon;
import org.springframework.data.repository.CrudRepository;

public interface HackathonRepository extends CrudRepository<Hackathon, Integer> {

    Iterable<Hackathon> findAllByTitleLikeIgnoreCase(String filter);
}
