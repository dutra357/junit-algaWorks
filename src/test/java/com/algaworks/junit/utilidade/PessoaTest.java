package com.algaworks.junit.utilidade;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PessoaTest {

    @Test
    public void assertivaAgrupadaTeste() {
        Pessoa pessoa = new Pessoa("Alex", "Green");

        Assertions.assertAll("Asserções de pessoa:",
                () -> Assertions.assertEquals("Alex", pessoa.getNome()),
                () -> Assertions.assertEquals("Green", pessoa.getSobrenome())
        );
    }

}