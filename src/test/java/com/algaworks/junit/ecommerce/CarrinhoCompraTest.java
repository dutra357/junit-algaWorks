package com.algaworks.junit.ecommerce;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.function.Executable;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CarrinhoCompraTest {

    Cliente cliente;
    Produto produto, produto2;
    ItemCarrinhoCompra item;
    CarrinhoCompra carrinhoCompra;
    List<ItemCarrinhoCompra> itens;

    @BeforeEach
    public void beforeEach() {
        cliente = new Cliente(1L, "Antonio");

        produto = new Produto(1L, "Meu produto",
                "Um produto muito bom para o meu uso.", new BigDecimal("200.00"));

        produto2 = new Produto(1L, "Outro Produto",
                "Um não muito bom.", new BigDecimal("19.99"));

        carrinhoCompra = new CarrinhoCompra(cliente);
    }

    @Test
    void getItens() {
        carrinhoCompra.adicionarProduto(produto, 2);
        Assertions.assertEquals(0, carrinhoCompra.getItens().size());
    }

    @Nested
    @DisplayName("Testes para adicionar/remover produtos")
    class Produtos{
        @BeforeEach
        public void setUp() {
            carrinhoCompra.adicionarProduto(produto, 2);
        }

        @Test
        @DisplayName("Adicionar produto nulo retorna ERRO")
        void adicionar_Produto_Nulo_Retorna_Erro() {
            Assertions.assertThrows(IllegalArgumentException.class, () -> {
                carrinhoCompra.adicionarProduto(null, 1);
            });
        }

        @Test
        void adicionar_Produto_Em_Quantidade_Menor_Que_Um_Retorna_Erro() {
            Assertions.assertThrows(IllegalArgumentException.class, () -> {
                carrinhoCompra.adicionarProduto(produto, 0);
            });
        }

        @Test
        void adicionar_Produto_igual_Valido() {
            carrinhoCompra.adicionarProduto(produto, 4);
            carrinhoCompra.adicionarProduto(produto2, 4);
            List<ItemCarrinhoCompra> itensCarrinho = carrinhoCompra.getItens();
            Assertions.assertEquals(10, carrinhoCompra.getQuantidadeTotalDeProdutos());
        }

        @Test
        void removerProdutoIndependenteDaQuantidade() {
            carrinhoCompra.removerProduto(produto);
            Assertions.assertEquals(0, carrinhoCompra.getQuantidadeTotalDeProdutos());
        }

        @Test
        void removerProdutoInexistenteRetornaErro() {
            carrinhoCompra.esvaziar();
            carrinhoCompra.adicionarProduto(produto2, 1);

            Assertions.assertEquals(1, carrinhoCompra.getQuantidadeTotalDeProdutos());
            Assertions.assertThrows(IllegalArgumentException.class, () -> {
                carrinhoCompra.removerProduto(produto);
            });
        }

        @Test
        void removerProdutoNuloRetornaErro() {
            Assertions.assertThrows(IllegalArgumentException.class, () -> {
                carrinhoCompra.removerProduto(null);
            });
        }

        @Test
        void aumentarQuantidadeProduto() {
            carrinhoCompra.aumentarQuantidadeProduto(produto);
            Assertions.assertEquals(3, carrinhoCompra.getQuantidadeTotalDeProdutos());
        }

        @Test
        void aumentarQuantidadeProdutoInexistenteRetornaErro() {
            Assertions.assertThrows(IllegalArgumentException.class, () -> {
                carrinhoCompra.aumentarQuantidadeProduto(produto2);
            });
        }

        @Test
        void aumentarQuantidadeProdutoNuloRetornaErro() {
            Assertions.assertThrows(IllegalArgumentException.class, () -> {
                carrinhoCompra.aumentarQuantidadeProduto(null);
            });
        }

        @Test
        void diminuirQuantidadeProdutoNulloRetornaErro() {
            Assertions.assertThrows(IllegalArgumentException.class, () -> {
                carrinhoCompra.diminuirQuantidadeProduto(null);
            });
        }

        @Test
        void diminuirQuantidadeProdutoComCarrinhoVazioRetornaErro() {
            carrinhoCompra.esvaziar();
            Assertions.assertThrows(IllegalArgumentException.class, () -> {
                carrinhoCompra.diminuirQuantidadeProduto(null);
            });
        }

        @Test
        void diminuirQuantidadeProdutoInexistenteRetornaErro() {
            Assertions.assertThrows(IllegalArgumentException.class, () -> {
                carrinhoCompra.diminuirQuantidadeProduto(produto2);
            });
        }

        @Test
        void diminuirQuantidadeProduto() {
            carrinhoCompra.diminuirQuantidadeProduto(produto);
            Assertions.assertEquals(1, carrinhoCompra.getQuantidadeTotalDeProdutos());
        }

        @Test
        void diminuirQuantidadeProdutoOndeQuandoZerarQuantidadeRemoverItemDaLista() {
            carrinhoCompra.diminuirQuantidadeProduto(produto);
            carrinhoCompra.diminuirQuantidadeProduto(produto);
            Assertions.assertEquals(0, carrinhoCompra.getQuantidadeTotalDeProdutos());
        }
    }
    @Nested
    @DisplayName("Testes sobre valores totais do pedido - quantidade/vaLor")
    class Totais {
        @BeforeEach
        public void setUp() {
            carrinhoCompra.adicionarProduto(produto, 2);
        }

        @Test
        void getValorTotal() {
            BigDecimal valorTotal = carrinhoCompra.getValorTotal();
            Assertions.assertEquals(new BigDecimal("400.00"), valorTotal);
        }

        @Test
        void getQuantidadeTotalDeProdutos() {
            carrinhoCompra.adicionarProduto(produto2, 5);
            Assertions.assertEquals(carrinhoCompra.getQuantidadeTotalDeProdutos(), 7);
        }
    }

    @Test
    void esvaziarDeveRemoverTodosOsItensDePedido() {
        carrinhoCompra.esvaziar();
        Assertions.assertEquals(0, carrinhoCompra.getQuantidadeTotalDeProdutos());
    }
}