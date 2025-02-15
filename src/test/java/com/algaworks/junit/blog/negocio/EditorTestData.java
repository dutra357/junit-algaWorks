package com.algaworks.junit.blog.negocio;

import com.algaworks.junit.blog.modelo.Editor;

import java.math.BigDecimal;

public class EditorTestData {

    private EditorTestData() {
    }

    //Design Pattern Object Mother - similar ao Factory e Builder
    public static Editor.Builder criaEditorNovo() {
        return Editor.builder()
                .withEmail("alex@gmail.com")
                .withNome("Alex")
                .withValorPagoPorPalavra(BigDecimal.TEN)
                .withPremium(true);
    }

    public static Editor.Builder criaEditorExistente() {
        return criaEditorNovo().withId(1L);
    }

    public static Editor.Builder criaEditorInexistente() {
        return criaEditorNovo().withId(99L);
    }

}
