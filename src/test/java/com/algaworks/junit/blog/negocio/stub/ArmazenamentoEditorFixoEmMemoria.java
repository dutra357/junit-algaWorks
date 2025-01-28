package com.algaworks.junit.blog.negocio.stub;

import com.algaworks.junit.blog.armazenamento.ArmazenamentoEditor;
import com.algaworks.junit.blog.modelo.Editor;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class ArmazenamentoEditorFixoEmMemoria implements ArmazenamentoEditor {

    public boolean here = false;
    @Override
    public Editor salvar(Editor editor) {
        if (editor.getId() == null) {
            editor.setId(1L);
        }
        here = true;
        return editor;
    }

    @Override
    public Optional<Editor> encontrarPorId(Long editor) {
        return Optional.empty();
    }

    @Override
    public Optional<Editor> encontrarPorEmail(String email) {
        if (email.equals("alex@gmail.com")) {
            return Optional.of(new Editor(1L, "Alex", "alex@gmail.com", BigDecimal.TEN, true));
        }
        return Optional.empty();
    }

    @Override
    public Optional<Editor> encontrarPorEmailComIdDiferenteDe(String email, Long id) {
        return Optional.empty();
    }

    @Override
    public void remover(Long editorId) {

    }

    @Override
    public List<Editor> encontrarTodos() {
        return null;
    }
}
