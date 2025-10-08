package com.dsbd.project.controller;

import com.dsbd.project.entity.Trip;
import com.dsbd.project.service.ProjectUserTripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/trips")
public class TripController {

    @Autowired
    private ProjectUserTripService userService;

    @GetMapping
    public Iterable<Trip> list() {
        return userService.listTrips();
    }

    @PostMapping("/new")
    public Trip create(@RequestBody Trip trip) {
        return userService.createTrip(trip);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteTrip(@PathVariable Integer id) {
        userService.deleteTrip(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{tripId}/buy")
    public ResponseEntity<?> buy(@AuthenticationPrincipal User principal, @PathVariable Integer tripId) {
        boolean ok = userService.buyTrip(principal.getUsername(), tripId);
        if (ok) return ResponseEntity.ok().build();
        return ResponseEntity.badRequest().body("Cannot buy trip");
    }
}


