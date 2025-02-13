package com.algaworks.junit.blog.negocio;

import com.algaworks.junit.blog.exception.RegraNegocioException;
import com.algaworks.junit.blog.modelo.Editor;
import com.algaworks.junit.blog.negocio.stub.ArmazenamentoEditorFixoEmMemoria;
import org.junit.jupiter.api.*;

import java.math.BigDecimal;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class CadastroEditorTest {

    static CadastroEditor cadastroEditor;
    private Editor editor;
    private static ArmazenamentoEditorFixoEmMemoria armazenamentoEditor;

    @BeforeEach
    void beforeEach() {
        armazenamentoEditor = new ArmazenamentoEditorFixoEmMemoria();

        cadastroEditor = new CadastroEditor(
                armazenamentoEditor,
                new GerenciadorEnvioEmail() { //Objeto duble sem STUB externo - casos simples
                    @Override
                    public void enviarEmail(Mensagem mensagem) {
                        System.out.println("Enviando mensagem para: " + mensagem.getDestinatario());
                    }
                });

        editor = new Editor(null, "Alex", "alex@gmail.com", BigDecimal.TEN, true);
    }

    @Test
    public void Dado_um_editor_valido_quando_criar_entao_deve_retornar_id_de_cadastro() {
        Editor editorCriado = cadastroEditor.criar(editor);
        Assertions.assertEquals(1L, editorCriado.getId());
        Assertions.assertTrue(armazenamentoEditor.here);
    }

    @Test
    public void Dado_um_editor_null_quando_criar_entao_deve_retornar_exception() {

        Assertions.assertThrows(NullPointerException.class, ()-> {
            Editor editorCriado = cadastroEditor.criar(null);
        });

        Assertions.assertFalse(armazenamentoEditor.here);
    }

    @Test
    public void Dado_um_editor_com_email_existente_quando_criar_entao_deve_lancar_exception() {

        Assertions.assertThrows(RegraNegocioException.class, ()-> {
            Editor editorCriado = cadastroEditor.criar(editor);
        });
    }

    @Test
    public void Dado_um_editor_com_email_existente_quando_criar_entao_nao_deve_salvar() {

        try {
            Editor editorCriado = cadastroEditor.criar(editor);
        } catch (RegraNegocioException e) {
            System.out.println(e.getMessage());
        }
    }
}