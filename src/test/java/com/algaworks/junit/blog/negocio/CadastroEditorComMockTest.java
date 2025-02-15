package com.algaworks.junit.blog.negocio;

import com.algaworks.junit.blog.armazenamento.ArmazenamentoEditor;
import com.algaworks.junit.blog.exception.EditorNaoEncontradoException;
import com.algaworks.junit.blog.exception.RegraNegocioException;
import com.algaworks.junit.blog.modelo.Editor;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@ExtendWith(MockitoExtension.class)
class CadastroEditorComMockTest {

    @Mock
    ArmazenamentoEditor armazenamentoEditor;
    @Mock
    GerenciadorEnvioEmail envioEmail;
    @InjectMocks
    static CadastroEditor cadastroEditor;

    private Editor editor;

    @Nested
    class CadastroComEditorValido {
        @Spy
        Editor editor = EditorTestData.criaEditorNovo();
        @BeforeEach
        void beforeEach() {

            //Customização do método na classe mockada - com passagem de parâmetro dinâmica (qualquer entidade Editor)
            Mockito.when(armazenamentoEditor.salvar(Mockito.any(Editor.class)))
                    .thenAnswer(invocationOnMock -> {
                        Editor novoEditor = invocationOnMock.getArgument(0, Editor.class);
                        novoEditor.setId(1L);
                        return novoEditor;
                    });

            //Mockito.when(armazenamentoEditor.salvar(Mockito.any(Editor.class))).thenReturn(editor);
        }
        @Test
        public void Dado_um_editor_valido_quando_criado_entao_deve_retornar_um_id_de_cadastro() {
            Editor editorSalvo = cadastroEditor.criar(editor);
            Assertions.assertEquals(1L, editorSalvo.getId());
        }

        @Test
        public void Dado_um_editor_valido_quando_criar_entao_deve_chamar_metodo_salvar_do_Armazenamento() {
            editor = cadastroEditor.criar(editor);

            Mockito.verify(armazenamentoEditor, Mockito.times(1)).salvar(Mockito.any(Editor.class));
        }

        @Test
        public void Dado_um_editor_valido_quando_cadastrar_entao_deve_enviar_email_com_destino_ao_editor() {
            //ArgumentCaptor - verifica o argumento passado ao método
            //Capturar do tipo MENSAGEM
            //Posso usar @Captor junto aos mocks
            ArgumentCaptor<Mensagem> mensagemArgumentCaptor = ArgumentCaptor.forClass(Mensagem.class);

            Editor editorCriado = cadastroEditor.criar(editor);

            Mockito.verify(envioEmail).enviarEmail(mensagemArgumentCaptor.capture());

            Mensagem mensagem = mensagemArgumentCaptor.getValue();

            Assertions.assertEquals(editorCriado.getEmail(), mensagem.getDestinatario());
        }

        @Test
        public void Dado_um_editor_valido_quando_cadastrar_entao_deve_verificar_o_email() {
            //Espionando objeto real com mockito
            //útil quando você deseja mockar parcialmente certos métodos de um objeto real, mantendo o comportamento real de outros
            //também há o @Spy - cuidar para nao substituir no foreach
            Editor editorSpy = Mockito.spy(editor);
            cadastroEditor.criar(editorSpy);

            Mockito.verify(editorSpy, Mockito.atLeast(1)).getEmail();
        }

        @Test
        public void Dado_um_Editor_com_Emai_existente_quando_cadastrar_entao_deve_lancar_exception() {
            Mockito.when(armazenamentoEditor.encontrarPorEmail("alex@gmail.com"))
                    .thenReturn(Optional.empty())
                    .thenReturn(Optional.of(editor));

            Editor editorExistente = EditorTestData.criaEditorNovo();

            cadastroEditor.criar(editor);
            Assertions.assertThrows(RegraNegocioException.class, () -> cadastroEditor.criar(editorExistente));
        }

        @Test
        public void Dado_um_editor_valido_quando_cadastrar_entao_deve_enviar_email_apos_salvar() {
            //Verificar se o arm.salvar está sendo chamado antes do envio de email
            cadastroEditor.criar(editor);

            InOrder inOrder = Mockito.inOrder(armazenamentoEditor, envioEmail);

            inOrder.verify(armazenamentoEditor, Mockito.times(1)).salvar(editor);
            inOrder.verify(envioEmail, Mockito.times(1)).enviarEmail(Mockito.any(Mensagem.class));
        }

        @Test
        public void Dado_um_editor_valido_ao_salvar_e_lancar_exception_nao_deve_enviar_email() {
            //AQUI - funciona sem o mock dinamico do BeForeEach
            Mockito.when(armazenamentoEditor.salvar(editor)).thenThrow(RuntimeException.class);

            Assertions.assertThrows(RuntimeException.class, () -> {
                cadastroEditor.criar(editor);
            });

            Mockito.verify(envioEmail, Mockito.never()).enviarEmail(any());
        }
    }

    @Nested
    class EdicaoComEditorValido {
        @Spy
        Editor editorSpy = EditorTestData.criaEditorExistente();
        @BeforeEach
        void beforeEach() {

            Mockito.when(armazenamentoEditor.salvar(editorSpy)).thenAnswer(invocation -> invocation.getArgument(0, Editor.class));
            Mockito.when(armazenamentoEditor.encontrarPorId(1L)).thenReturn(Optional.of(editorSpy));
        }

        @Test
        public void Dado_um_editor_valido_quando_editar_entao_deve_Alterar_editor_salvo() {

            Editor atualizado = EditorTestData.criaEditorExistente();
            atualizado.setEmail("atualizado@gmail.com");
            atualizado.setNome("Atualizado da Silva");

            cadastroEditor.editar(atualizado);

            Mockito.verify(editorSpy, Mockito.times(1)).atualizarComDados(atualizado);

            InOrder inOrder = Mockito.inOrder(editorSpy, armazenamentoEditor);
            inOrder.verify(editorSpy).atualizarComDados(atualizado);
            inOrder.verify(armazenamentoEditor).salvar(editorSpy);
        }
    }

    @Nested
    class EdicaoComEditorInexistente {
        @BeforeEach
        public void beforeEach() {

            editor = EditorTestData.criaEditorInexistente();

            Mockito.when(armazenamentoEditor.encontrarPorId(99L)).thenReturn(Optional.empty());
        }

        @Test
        public void Dado_um_editor_inexistente_quando_editar_entao_lancar_exception() {
            Assertions.assertThrows(EditorNaoEncontradoException.class, () ->
                    cadastroEditor.editar(editor));

            Mockito.verify(armazenamentoEditor, Mockito.never()).salvar(Mockito.any(Editor.class));
        }
    }

    @Nested
    class CadastroComEditorNull {
        @Test
        public void Dado_um_editor_null_quando_cadastrar_entao_deve_lancar_exception() {
            //Aqui o mockito lança exception em razao de o comportamento mockado nao ser utilizado por todos os testes
            //solucao 1 - ruim. colocar o comportamento mochado em cada teste
            //solucao 2 - criar inner Class
            Assertions.assertThrows(NullPointerException.class, () -> cadastroEditor.criar(null));

            Mockito.verify(armazenamentoEditor, Mockito.never()).salvar(Mockito.any(Editor.class));
            Mockito.verify(envioEmail, Mockito.never()).enviarEmail(Mockito.any());
        }
    }
}