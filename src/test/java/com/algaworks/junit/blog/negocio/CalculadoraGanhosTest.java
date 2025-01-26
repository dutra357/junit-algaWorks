package com.algaworks.junit.blog.negocio;

import com.algaworks.junit.blog.modelo.Editor;
import com.algaworks.junit.blog.modelo.Ganhos;
import com.algaworks.junit.blog.modelo.Post;
import com.algaworks.junit.blog.utilidade.ProcessadorTextoSimples;
import org.junit.jupiter.api.*;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class CalculadoraGanhosTest {

    static CalculadoraGanhos calculadoraGanhos;
    private Editor editorAlex;
    private Post postEcosistema;

    @BeforeAll
    public static void beforeAll() {
        calculadoraGanhos = new CalculadoraGanhos(new ProcessadorTextoSimples(), BigDecimal.TEN);

        System.out.println("Before ALL!");
    }
    @BeforeEach
    public void beforeEach() {
        editorAlex = new Editor(1L, "alex@gmail.com", "Alex Green", new BigDecimal(5), true);

        postEcosistema = new Post(1L, "Ecosistema JAVA", "O Ecosistema do Java é muito maduro.",
                editorAlex, "ecosistema-java-abc-123",
                null, false, false);

        System.out.println("BEFORE Each!");
    }

    @Test
    public void deveCalcularGanhos() {

        Ganhos ganhos = calculadoraGanhos.calcular(postEcosistema);

        Assertions.assertEquals(new BigDecimal(45), ganhos.getTotalGanho());
        Assertions.assertEquals(7, ganhos.getQuantidadePalavras());
        Assertions.assertEquals(editorAlex.getValorPagoPorPalavra(), ganhos.getValorPagoPorPalavra());
    }

    @Test
    public void deveCalcularGanhosSemPremium() {
        editorAlex.setPremium(false);

        Ganhos ganhos = calculadoraGanhos.calcular(postEcosistema);

        Assertions.assertEquals(new BigDecimal(35), ganhos.getTotalGanho());
        Assertions.assertEquals(7, ganhos.getQuantidadePalavras());
        Assertions.assertEquals(editorAlex.getValorPagoPorPalavra(), ganhos.getValorPagoPorPalavra());
    }

}