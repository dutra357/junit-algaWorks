package com.algaworks.junit.blog.negocio;

import com.algaworks.junit.blog.modelo.Editor;

import java.math.BigDecimal;

public class EditorTestData {

    //Design Pattern Object Mother - similar ao Factory
    public static Editor criaEditorNovo() {
        return new Editor(null, "Alex", "alex@gmail.com", BigDecimal.TEN, true);
    }

    public static Editor criaEditorExistente() {
        return new Editor(1L, "Alex", "alex@gmail.com", BigDecimal.TEN, true);
    }

    public static Editor criaEditorInexistente() {
        return new Editor(99L, "Alex", "alex@gmail.com", BigDecimal.TEN, true);
    }
}
