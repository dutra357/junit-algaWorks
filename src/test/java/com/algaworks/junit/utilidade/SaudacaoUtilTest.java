package com.algaworks.junit.utilidade;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;

class SaudacaoUtilTest {

    @Test
    public void saudarTestManha() {
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

    @Test
    public void saudarTestTarde() {
        String saudacao = SaudacaoUtil.saudar(12);
        System.out.println(saudacao);

        Assertions.assertEquals("Boa tarde", saudacao, "Saudação incorreta!");
        Assertions.assertTrue(saudacao.equals("Boa tarde"));
    }

    @Test
    public void saudarTestNoite() {
        String saudacao = SaudacaoUtil.saudar(22);
        System.out.println(saudacao);

        Assertions.assertEquals("Boa noite", saudacao, "Saudação incorreta!");
        Assertions.assertTrue(saudacao.equals("Boa noite"));
    }

    @Test
    public void saudarTestAposCincoComBomDia() {
        String saudacao = SaudacaoUtil.saudar(5);
        System.out.println(saudacao);

        Assertions.assertEquals("Bom dia", saudacao, "Saudação incorreta!");
        Assertions.assertTrue(saudacao.equals("Bom dia"));
    }

    @Test
    public void saudarTestAntesDasCincoComBomDia() {
        String saudacao = SaudacaoUtil.saudar(3);
        System.out.println(saudacao);

        Assertions.assertEquals("Boa noite", saudacao, "Saudação incorreta!");
        Assertions.assertTrue(saudacao.equals("Boa noite"));
    }



    @Test
    @Disabled("Repetido para teste - Teste não realziado")
    public void saudarTestDesabilitado() {

        Assertions.assertDoesNotThrow(() -> SaudacaoUtil.saudar(5));
    }

    @Test
    //@EnabledIfEnvironmentVariable(named = "ENV", matches = "DEV")
    //Repetido para teste, desabilitado por variavel de ambiente
    public void saudarTestDesabilitadoPorVariavelDeAmbiente() {
        //Desabilitado por variável ou Assumptions
        Assumptions.assumeTrue("DEV".equals(System.getenv("ENV")), () -> "Abortando teste.");

        Assertions.assertDoesNotThrow(() -> SaudacaoUtil.saudar(5));
    }

}