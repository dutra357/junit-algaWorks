package com.algaworks.junit.blog.utilidade;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;


class ConversorSlugTest {

    @Test
    @Disabled("Teste para metodo estatico dependente de mockito.inline")
    public void deveConverterJuntoComCodigo(){

        //Teste de metodos static é uma funcionalidade experimental no mockito
        //nao estavel - vem em pacote separado (mockito.core para mockito.inline) - desabilitado nas dependencias
        //mais custoso para a JVM
        try (MockedStatic<GeradorCodigo> geradorCodigoMockedStatic = Mockito.mockStatic(GeradorCodigo.class)) {

            geradorCodigoMockedStatic.when(GeradorCodigo::gerar).thenReturn("123456");

            String slug = ConversorSlug.converterJuntoComCodigo("olá mundo");

            Assertions.assertEquals("ola-mundo-123456", slug);
        }


    }
}