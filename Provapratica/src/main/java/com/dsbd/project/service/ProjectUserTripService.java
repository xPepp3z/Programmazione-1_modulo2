package com.dsbd.project.service;

import com.dsbd.project.security.JwtUtils;
import com.dsbd.project.entity.User;
import com.dsbd.project.entity.UserRepository;
import com.dsbd.project.security.AuthResponse;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Optional;

import org.springframework.util.Assert;
import com.dsbd.project.entity.Trip;
import com.dsbd.project.entity.TripRepository;
import org.springframework.web.server.ResponseStatusException;

@Service
@Transactional
public class ProjectUserTripService {

    @Autowired
    UserRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    JwtUtils jwtUtils;

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Autowired
    private TripRepository tripRepository;

    public User addUser(User user) {
        user.setRoles(Collections.singletonList("USER"));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return repository.save(user);
    }

    public Iterable<User> getAllUsers() {
        return repository.findAll();
    }

    public Optional<User> getByEmail(String email) {
        return Optional.ofNullable(repository.findByEmail(email));
    }
    public User topUpCredit(String email, BigDecimal amount) {
        Assert.notNull(email, "email must not be null");
        Assert.notNull(amount, "amount must not be null");
        User user = repository.findByEmail(email);
        if (user == null) return null;
        user.setCredit(user.getCredit().add(amount));
        return repository.save(user);
    }

    public boolean buyTrip(String email, Integer tripId) {
        User user = repository.findByEmail(email);
        if (user == null) return false;
        Optional<Trip> tripOpt = tripRepository.findById(tripId);
        if (!tripOpt.isPresent()) return false;
        Trip trip = tripOpt.get();
        if (trip.getPrice() == null) return false;
        if (user.getCredit().compareTo(trip.getPrice()) < 0) return false;
        user.setCredit(user.getCredit().subtract(trip.getPrice()));
        repository.save(user);
        return true;
    }

    public Iterable<Trip> listTrips() {
        return tripRepository.findAll();
    }

    public Trip createTrip(Trip trip) {
        return tripRepository.save(trip);
    }

    public AuthResponse login(User user) {
        User u = repository.findByEmail(user.getEmail());
        if (u != null && passwordEncoder.matches(user.getPassword(), u.getPassword())) {
            String access = jwtUtils.generateJwtToken(u.getEmail(), u.getRoles());
            String refresh = jwtUtils.generateRefreshToken(u.getEmail());
            return new AuthResponse(access, refresh);
        }
        return null;
    }

    public AuthResponse reAuth( String refreshToken) throws Exception {
        AuthResponse authResponse = new AuthResponse();
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(jwtSecret)
                    .parseClaimsJws(refreshToken)
                    .getBody();
            User user = repository.findByEmail(claims.getSubject());
            if (user != null) {
                 authResponse.setAccessToken(jwtUtils.generateJwtToken(user.getEmail(), user.getRoles()));
                 authResponse.setRefreshToken(jwtUtils.generateRefreshToken(user.getEmail()));
            }
        } catch (Exception e) {
            // Gestisci eventuali errori durante la decodifica del token
            authResponse.setMsg(String.valueOf(e));
            throw new Exception("Errore durante la decodifica del token", e);
        } finally {
            return authResponse;
        }
    }


    public AuthResponse refresh(User user) {
        User u = repository.findByEmail(user.getEmail());
        if (u != null) {
            if (passwordEncoder.matches(user.getPassword(), u.getPassword())) {
                AuthResponse authResponse = new AuthResponse(jwtUtils.generateJwtToken(u.getEmail()), jwtUtils.generateRefreshToken(u.getEmail()));
                return authResponse;
            }
        }
        return null;
    }

    public String deleteUser(Integer userId) {
        repository.deleteById(userId);
        return "User with id: "+userId+" has been deleted!";
    }

    public void deleteTrip(Integer id) {
        Trip trip = tripRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Trip not found"));
        tripRepository.delete(trip);
    }

}
