package com.algaworks.junit.blog.negocio;

import com.algaworks.junit.blog.modelo.Editor;
import org.junit.jupiter.api.*;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class CadastroEditorTest {

    static CadastroEditor cadastroEditor;
    private Editor editor;

    @BeforeAll
    static void beforeAll() {
        cadastroEditor = new CadastroEditor(new ArmazenamentoEditorFixoEmMemoria(),
                new GerenciadorEnvioEmail() {
                @Override
                    public void enviarEmail(Mensagem mensagem) {
                    System.out.println("Enviando mensagem para: " + mensagem.getDestinatario());
                }
                });
    }

    @BeforeEach
    void beforeEach() {
        editor = new Editor(null, "Alex", "alex@gmail.com", BigDecimal.TEN, true);
    }

    @Test
    public void Dado_um_editor_valido_quando_criar_entao_deve_retornar_id_de_cadastro() {
        Editor editorCriado = cadastroEditor.criar(editor);
        Assertions.assertEquals(1L, editorCriado.getId());
    }
}