package com.dsbd.project.controller;

import com.dsbd.project.entity.User;
import com.dsbd.project.security.AuthResponse;
import com.dsbd.project.service.ProjectUserTripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(path = "/user")
public class UserController {

    @Autowired
    ProjectUserTripService userService;

    //POST http://localhost:8080/user/register
    @PostMapping(path = "/register")
    public @ResponseBody User register(@RequestBody User user) {
        return userService.addUser(user);
    }

    @PostMapping(path = "/login")
    public @ResponseBody AuthResponse login(@RequestBody User user) {
        return userService.login(user);
    }

    @PostMapping(path = "/re-auth")
    public @ResponseBody AuthResponse reAuth (@RequestHeader String refreshToken) throws Exception {
        return userService.reAuth(refreshToken);
    }
    // GET /user/me - Restituisce i dati dell'utente autenticato
    @GetMapping(path = "/me")
    public @ResponseBody User getAuthenticatedUser(@AuthenticationPrincipal org.springframework.security.core.userdetails.User principal) {
        if (principal == null) return null;
        return userService.getByEmail(principal.getUsername()).orElse(null);
    }

    // PATCH /user/me/credit/topup - Ricarica il credito dell'utente autenticato
    @PatchMapping(path = "/me/credit/topup")
    public @ResponseBody User topUpCredit(
            @AuthenticationPrincipal org.springframework.security.core.userdetails.User principal,
            @RequestBody TopUpRequest topUpRequest) {
        return userService.topUpCredit(principal.getUsername(), topUpRequest.getAmount());
    }
}
