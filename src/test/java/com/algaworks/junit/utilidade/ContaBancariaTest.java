package com.algaworks.junit.utilidade;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class ContaBancariaTest {
    @Nested
    class Saque {
        @Test
        void saqueLançaIllegalArgumentExceptionParaValorZero() {
            Assertions.assertThrows(IllegalArgumentException.class,
                    ()-> {
                        ContaBancaria contaBancaria = new ContaBancaria(new BigDecimal(10));
                        contaBancaria.saque(new BigDecimal(0));
                    });
        }

        @Test
        void saqueLançaIllegalArgumentExceptionParaValorNulo() {
            Assertions.assertThrows(IllegalArgumentException.class,
                    ()-> {
                        ContaBancaria contaBancaria = new ContaBancaria(new BigDecimal(10));
                        contaBancaria.saque(null);
                    });
        }

        @Test
        void saqueLançaIllegalArgumentExceptionParaValorNegativo() {
            Assertions.assertThrows(IllegalArgumentException.class,
                    ()-> {
                        ContaBancaria contaBancaria = new ContaBancaria(new BigDecimal(10));
                        contaBancaria.saque(new BigDecimal(-2));
                    });
        }

        @Test
        void saqueLançaRuntimeExceptionParaSaldoInsuficiente() {
            Assertions.assertThrows(RuntimeException.class,
                    ()-> {
                        ContaBancaria contaBancaria = new ContaBancaria(new BigDecimal(10));
                        contaBancaria.saque(new BigDecimal(20));
                    });
        }

        @Test
        void saqueDebitaDoSaldoParaValorVálido() {
            ContaBancaria contaBancaria = new ContaBancaria(new BigDecimal(10));
            contaBancaria.saque(new BigDecimal(5));

            Assertions.assertEquals(new BigDecimal(5), contaBancaria.saldo());
        }
    }

    @Nested
    class Deposito {
        @Test
        void depositoDeveLançarIllegalArgumentExceptionParaValorNulo() {
            Assertions.assertThrows(IllegalArgumentException.class,
                    ()-> {
                        ContaBancaria contaBancaria = new ContaBancaria(new BigDecimal(10));
                        contaBancaria.saque(null);
                    });
        }

        @Test
        void depositoDeveLançarIllegalArgumentExceptionParaValorNegativo() {
            Assertions.assertThrows(IllegalArgumentException.class,
                    ()-> {
                        ContaBancaria contaBancaria = new ContaBancaria(new BigDecimal(10));
                        contaBancaria.saque(new BigDecimal(-10));
                    });
        }

        @Test
        void depositoDeveLançarIllegalArgumentExceptionParaValorZero() {
            Assertions.assertThrows(IllegalArgumentException.class,
                    ()-> {
                        ContaBancaria contaBancaria = new ContaBancaria(new BigDecimal(10));
                        contaBancaria.saque(new BigDecimal(0));
                    });
        }

        @Test
        void depositoDeveAtualizarSaldoParaDepositoValido() {
            ContaBancaria contaBancaria = new ContaBancaria(new BigDecimal(10));
            contaBancaria.deposito(new BigDecimal(5));

            Assertions.assertEquals(new BigDecimal(15), contaBancaria.saldo());
        }
    }


    @Test
    void contaConstructorShouldReturnExceptioWhenSaldoIsNull() {
        Assertions.assertThrows(IllegalArgumentException.class,
                ()-> {
                    ContaBancaria contaBancaria = new ContaBancaria(null);
                });
    }

    @Test
    void contaConstructorShouldEffectiveDepositWhenValorIsValid() {
        ContaBancaria contaBancaria = new ContaBancaria(new BigDecimal(10));
        Assertions.assertEquals(new BigDecimal(10), contaBancaria.saldo());
    }

    @Test
    void saldoDeveRetornarSaldoPresente() {
        ContaBancaria contaBancaria = new ContaBancaria(new BigDecimal(10));

        Assertions.assertEquals(new BigDecimal(10), contaBancaria.saldo());
    }
}