package com.algaworks.junit.blog.negocio;

import com.algaworks.junit.blog.armazenamento.ArmazenamentoEditor;
import com.algaworks.junit.blog.exception.RegraNegocioException;
import com.algaworks.junit.blog.modelo.Editor;
import com.algaworks.junit.blog.negocio.stub.ArmazenamentoEditorFixoEmMemoria;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

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

        Mockito.when(armazenamentoEditor.salvar(any()))
                .thenReturn(new Editor(1L, "Alex", "alex@gmail.com", BigDecimal.TEN, true));

    }

    @Test
    public void Dado_um_editor_valido_quando_criado_entao_deve_retornar_um_id_de_cadastro() {
        Editor editorSalvo = cadastroEditor.criar(editor);

        Assertions.assertEquals(1L, editorSalvo.getId());
    }
}