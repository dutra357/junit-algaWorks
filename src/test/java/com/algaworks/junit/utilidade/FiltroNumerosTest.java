package com.algaworks.junit.utilidade;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;


class FiltroNumerosTest {

    @Test
    public void numerosParesDevemRetornarPares() {
        List<Integer> numeros = Arrays.asList(1, 2, 3, 4, 5, 6, 7);
        List<Integer> paresEsperados = Arrays.asList(2, 4, 6);

        List<Integer> resultado = FiltroNumeros.numerosPares(numeros);

        //assertIterableEquals verifica a ordem também. usa o equals do próprio objeto
        Assertions.assertIterableEquals(paresEsperados, resultado);
    }
}