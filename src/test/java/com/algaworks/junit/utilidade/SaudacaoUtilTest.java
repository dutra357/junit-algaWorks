package com.algaworks.junit.utilidade;

import org.assertj.core.api.Condition;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class SaudacaoUtilTest {

    @Test
    public void saudar_Test_para_Manha() {
        String saudacao = SaudacaoUtil.saudar(9);
        System.out.println(saudacao);

        Assertions.assertEquals("Bom dia", saudacao, "Saudação incorreta!");
        Assertions.assertTrue(saudacao.equals("Bom dia"));


        //Condition (asserj-core)
        //Pode ser criado um construtor estático para gerar Conditions em classe utils
        Condition<String> condition = new Condition<>((s) -> s.equals("Bom dia"), "Parametro: %s", "Bom dia");
        org.assertj.core.api.Assertions.assertThat(saudacao)
                .is(condition);


        //Módulo asserJ
        org.assertj.core.api.Assertions.assertThat(saudacao)
                //asserçõs com msg descritivas
                .as("Valdiando se a saudacao correta é %s: ", "Bom dia")
                .withFailMessage("Saudação incorreta!")
                .isEqualTo("Bom dia");
    }

    @Test
    public void saudarTestMustThrowException() {

        IllegalArgumentException illegalArgumentException = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            String saudacao = SaudacaoUtil.saudar(-9);
            System.out.println(saudacao);
        });

        Assertions.assertEquals("Hora inválida", illegalArgumentException.getMessage());

        //AssertJ - catchThrowableOfType()
        IllegalArgumentException e = org.assertj.core.api.Assertions.catchThrowableOfType( () -> SaudacaoUtil.saudar(-9), IllegalArgumentException.class);
        org.assertj.core.api.Assertions.assertThat(e)
                .hasMessage("Hora inválida");
    }

    @Test
    @DisplayName("Deve saudar com bom dia sem lançar exceção.")
    public void saudarTestShouldNotThrowException() {
        //refatorado para padrão AAA
        //Assertions.assertDoesNotThrow(() -> SaudacaoUtil.saudar(5));

        //Arrange
        int horaSaudacao = 5;

        //Act
        Executable executable = () -> SaudacaoUtil.saudar(horaSaudacao);

        //Assert
        Assertions.assertDoesNotThrow(executable);
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

    @ParameterizedTest
    @ValueSource(ints = {5, 6, 7, 8, 9, 10, 11 })
    public void Dado_horario_matinal_entao_deve_retornar_bom_dia(int hora) {

        //Teste parametrizado - dispensa @Test
        //Dependencia especifica
        String saudacao = SaudacaoUtil.saudar(hora);

        Assertions.assertEquals("Bom dia", saudacao, "Saudação incorreta!");
        Assertions.assertTrue(saudacao.equals("Bom dia"));

    }

}