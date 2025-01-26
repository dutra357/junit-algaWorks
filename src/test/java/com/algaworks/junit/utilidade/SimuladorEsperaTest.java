package com.algaworks.junit.utilidade;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

class SimuladorEsperaTest {

    @Test
    void deveEsperarSemDarTimeOut() {
        //Aqui ele vai aguardar
        Assertions.assertTimeout(Duration.ofSeconds(2),
                () -> SimuladorEspera.esperar(Duration.ofSeconds(1)));
    }

    @Test
    void deveTesteTimeoutImediato() {
        //Aqui encerra praventivamente, não precisando esgotar o tempo da função executada
        //caso maior do que o parametro de timeout
        Assertions.assertTimeoutPreemptively(Duration.ofSeconds(2),
                () -> SimuladorEspera.esperar(Duration.ofSeconds(1)));
    }

}