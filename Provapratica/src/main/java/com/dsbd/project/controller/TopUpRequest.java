package com.dsbd.project.controller;

import java.math.BigDecimal;

public class TopUpRequest {
    private BigDecimal amount;

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    //Funge da DTO, quindi il tramite tra backend e frontend, richiama solo una parte invece di tutto il file originale
}


