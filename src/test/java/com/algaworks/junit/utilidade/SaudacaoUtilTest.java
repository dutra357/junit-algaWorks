package com.algaworks.junit.utilidade;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class SaudacaoUtilTest {

    @Test
    public void saudarTest() {
        String saudacao = SaudacaoUtil.saudar(9);
        System.out.println(saudacao);

        Assertions.assertEquals("Bom dia", saudacao, "Saudação incorreta!");
        Assertions.assertTrue(saudacao.equals("Bom dia"));
    }

    @Test
    public void saudarTestMustThrowException() {

        IllegalArgumentException illegalArgumentException = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            String saudacao = SaudacaoUtil.saudar(-9);
            System.out.println(saudacao);
        });

        Assertions.assertEquals("Hora inválida", illegalArgumentException.getMessage());
    }

    @Test
    public void saudarTestShouldNotThrowException() {

        Assertions.assertDoesNotThrow(() -> SaudacaoUtil.saudar(5));
    }

}