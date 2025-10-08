package com.dsbd.project.entity;



import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
public class Trip {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NotNull(message = "Origin must not be blank!")
    private String origin;

    @NotNull(message = "Destination must not be blank!")
    private String destination;

    @NotNull(message = "Departure time must not be blank!")
    private LocalDateTime departureTime;

    @PositiveOrZero(message = "Price must be zero or positive!")
    private BigDecimal price;

    // Getters & Setters
    public Integer getId() {
        return id;
    }

    public Trip setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getOrigin() {
        return origin;
    }

    public Trip setOrigin(String origin) {
        this.origin = origin;
        return this;
    }

    public String getDestination() {
        return destination;
    }

    public Trip setDestination(String destination) {
        this.destination = destination;
        return this;
    }

    public LocalDateTime getDepartureTime() {
        return departureTime;
    }

    public Trip setDepartureTime(LocalDateTime departureTime) {
        this.departureTime = departureTime;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Trip setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }
}
