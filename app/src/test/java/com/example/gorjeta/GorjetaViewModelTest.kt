package com.example.gorjeta

import org.junit.Assert.*
import org.junit.Test

class GorjetaViewModelTest {
    private fun modelo(conta: String = "100", percentual: String = "10", pessoas: String = "2") =
        GorjetaViewModel().apply {
            onValorContaChange(conta)
            onPercentualGorjetaChange(percentual)
            onNumeroPessoasChange(pessoas)
        }

    private fun verificarErro(conta: String, percentual: String, pessoas: String, mensagem: String) {
        val vm = modelo(conta, percentual, pessoas)
        vm.calcular()
        assertEquals(mensagem, vm.uiState.value.mensagemErro)
        assertNull(vm.uiState.value.valorGorjeta)
        assertNull(vm.uiState.value.valorTotal)
        assertNull(vm.uiState.value.valorPorPessoa)
    }

    @Test fun estadoInicial() { assertEquals(GorjetaUiState(), GorjetaViewModel().uiState.value) }

    @Test fun calculaConta() {
        val vm = modelo()
        vm.calcular()
        assertEquals(10.0, vm.uiState.value.valorGorjeta!!, 0.0001)
        assertEquals(110.0, vm.uiState.value.valorTotal!!, 0.0001)
        assertEquals(55.0, vm.uiState.value.valorPorPessoa!!, 0.0001)
        assertNull(vm.uiState.value.mensagemErro)
    }

    @Test fun aceitaVirgula() {
        val vm = modelo("100,50", "10,5", "3")
        vm.calcular()
        assertEquals(10.5525, vm.uiState.value.valorGorjeta!!, 0.0001)
        assertEquals(37.0175, vm.uiState.value.valorPorPessoa!!, 0.0001)
    }

    @Test fun aceitaGorjetaZero() {
        val vm = modelo(percentual = "0")
        vm.calcular()
        assertEquals(0.0, vm.uiState.value.valorGorjeta!!, 0.0001)
        assertEquals(50.0, vm.uiState.value.valorPorPessoa!!, 0.0001)
    }

    @Test fun camposVaziosOuInvalidos() {
        listOf("", "abc", "NaN", "Infinity").forEach {
            verificarErro(it, "10", "2", "Preencha todos os campos com valores válidos.")
            verificarErro("100", it, "2", "Preencha todos os campos com valores válidos.")
        }
        verificarErro("100", "10", "", "Preencha todos os campos com valores válidos.")
    }

    @Test fun rejeitaContaNaoPositiva() {
        listOf("0", "-10").forEach {
            verificarErro(it, "10", "2", "O valor da conta deve ser maior que zero.")
        }
    }

    @Test fun rejeitaPessoasNaoPositivas() {
        listOf("0", "-1").forEach {
            verificarErro("100", "10", it, "Informe ao menos 1 pessoa.")
        }
    }

    @Test fun rejeitaPessoasFracionarias() {
        verificarErro("100", "10", "1.5", "Preencha todos os campos com valores válidos.")
    }

    @Test fun rejeitaGorjetaNegativa() {
        verificarErro("100", "-1", "2", "O percentual de gorjeta não pode ser negativo.")
    }

    @Test fun edicaoLimpaResultado() {
        val vm = modelo()
        vm.calcular()
        vm.onValorContaChange("200")
        assertNull(vm.uiState.value.valorTotal)
        assertEquals("200", vm.uiState.value.valorConta)
        vm.calcular()
        vm.onPercentualGorjetaChange("15")
        assertNull(vm.uiState.value.valorGorjeta)
        vm.calcular()
        vm.onNumeroPessoasChange("3")
        assertNull(vm.uiState.value.valorPorPessoa)
    }

    @Test fun corrigeErro() {
        val vm = modelo(conta = "0")
        vm.calcular()
        vm.onValorContaChange("100")
        assertNull(vm.uiState.value.mensagemErro)
        vm.calcular()
        assertEquals(110.0, vm.uiState.value.valorTotal!!, 0.0001)
    }

    @Test fun rejeitaOverflow() {
        verificarErro("1e308", "1e308", "1", "Preencha todos os campos com valores válidos.")
    }
}
