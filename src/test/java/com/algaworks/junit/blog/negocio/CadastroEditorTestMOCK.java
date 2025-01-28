package com.algaworks.junit.blog.negocio;

import com.algaworks.junit.blog.armazenamento.ArmazenamentoEditor;
import com.algaworks.junit.blog.exception.RegraNegocioException;
import com.algaworks.junit.blog.modelo.Editor;
import com.algaworks.junit.blog.negocio.stub.ArmazenamentoEditorFixoEmMemoria;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@ExtendWith(MockitoExtension.class)
class CadastroEditorTestMOCK {

    @Mock
    ArmazenamentoEditor armazenamentoEditor;
    @Mock
    GerenciadorEnvioEmail envioEmail;

    @InjectMocks
    static CadastroEditor cadastroEditor;
    private Editor editor;

    @BeforeEach
    void beforeEach() {
        editor = new Editor(null, "Alex", "alex@gmail.com", BigDecimal.TEN, true);

        //Customização do método na classe mockada - com customização estática (Editor fixo)
//        Mockito.when(armazenamentoEditor.salvar(any()))
//                .thenAnswer(invocationOnMock -> {
//                    Editor novoEditor = invocationOnMock.getArgument(0, Editor.class);
//                    novoEditor.setId(1L);
//                    return novoEditor;
//                });

        //Customização do método na classe mockada - com passagem de parâmetro dinâmica (qualquer entidade Editor)
        Mockito.when(armazenamentoEditor.salvar(any(Editor.class)))
                .thenAnswer(invocationOnMock -> {
                    Editor novoEditor = invocationOnMock.getArgument(0, Editor.class);
                    novoEditor.setId(1L);
                    return novoEditor;
                });

    }

    @Test
    public void Dado_um_editor_valido_quando_criado_entao_deve_retornar_um_id_de_cadastro() {
        Editor editorSalvo = cadastroEditor.criar(editor);

        Assertions.assertEquals(1L, editorSalvo.getId());
    }

    @Test
    public void Dado_um_editor_valido_quando_criar_entao_deve_chamar_metodo_salvar_do_Armazenamento() {
        Editor editorSalvo = cadastroEditor.criar(editor);

        Mockito.verify(armazenamentoEditor, Mockito.times(1)).salvar(Mockito.any(Editor.class));
    }

    @Test
    public void Dado_um_editor_valido_ao_salvar_e_lancar_exception_nao_deve_enviar_email() {

        Mockito.when(armazenamentoEditor.salvar(editor)).thenThrow(RuntimeException.class);

        Assertions.assertThrows(RuntimeException.class, () -> {
            cadastroEditor.criar(editor);
        });

        Mockito.verify(envioEmail, Mockito.never()).enviarEmail(any());
    }

    @Test
    public void Dado_um_editor_valido_quando_cadastrar_entao_deve_enviar_email_com_destino_ao_editor() {

        ArgumentCaptor<Mensagem> mensagemArgumentCaptor = ArgumentCaptor.forClass(Mensagem.class);

        Editor editorCriado = cadastroEditor.criar(editor);

        Mockito.verify(envioEmail).enviarEmail(mensagemArgumentCaptor.capture());

        Mensagem mensagem = mensagemArgumentCaptor.getValue();

        Assertions.assertEquals(editorCriado.getEmail(), mensagem.getDestinatario());
    }

    @Test
    public void Dado_um_editor_valido_quando_cadastrar_entao_deve_verificar_o_email() {

        Editor editorSpy = Mockito.spy(editor);
        cadastroEditor.criar(editorSpy);

        Mockito.verify(editorSpy, Mockito.atLeast(1)).getEmail();
    }
}