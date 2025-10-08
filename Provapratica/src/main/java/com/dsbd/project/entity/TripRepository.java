package com.dsbd.project.entity;

import com.dsbd.project.entity.Trip;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TripRepository extends CrudRepository<Trip, Integer> {
    // Se vuoi puoi aggiungere query custom, tipo:
    // List<Trip> findByOriginAndDestination(String origin, String destination);
}
