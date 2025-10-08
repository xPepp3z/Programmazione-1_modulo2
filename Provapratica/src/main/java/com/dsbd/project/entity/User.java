package com.dsbd.project.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;


@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NotNull(message = "The email parameter must not be blank!")
    @Column(unique = true)
    private String email;

    @NotNull(message = "The password parameter must not be blank!")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @NotNull(message = "The credit parameter must not be blank!")
    private BigDecimal credit = BigDecimal.ZERO;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> roles;

    public User() {} // costruttore vuoto obbligatorio

    public User(BigDecimal credit) {
        this.credit = credit;
    }

    public Integer getId() { return id; }
    public User setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getEmail() { return email; }
    public User setEmail(String email) {
        this.email = email; return this;
    }

    public String getPassword() { return password; }
    public User setPassword(String password) {
        this.password = password;
        return this;
    }

    public BigDecimal getCredit() { return credit; }
    public User setCredit(BigDecimal credit) {
        this.credit = credit; return this;
    }

    public List<String> getRoles() { return roles; }
    public User setRoles(List<String> roles) {
        this.roles = roles;
        return this;
    }
}
