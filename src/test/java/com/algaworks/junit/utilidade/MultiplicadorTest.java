package com.algaworks.junit.utilidade;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;


class MultiplicadorTest {

    //Carregando valores de um Enum - parametrizado
    @ParameterizedTest
    @EnumSource(value = Multiplicador.class, names = { "DOBRO", "TRIPLO" } )
    public void aplicarMultiplicador(Multiplicador multiplicador) {

        Assertions.assertNotNull(multiplicador.aplicarMultiplicador(10.0));
    }


    //Aqui testa com todos os possiveis valores do enum
    @ParameterizedTest
    @EnumSource(value = Multiplicador.class)
    public void aplicarMultiplicadorComTodasAsOpcoes(Multiplicador multiplicador) {

        Assertions.assertNotNull(multiplicador.aplicarMultiplicador(10.0));
    }
}