package com.example.gorjeta

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class GorjetaViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(GorjetaUiState())
    val uiState: StateFlow<GorjetaUiState> = _uiState.asStateFlow()

    fun onValorContaChange(valor: String) {
        _uiState.value = _uiState.value.copy(valorConta = valor)
        limparResultado()
    }

    fun onPercentualGorjetaChange(valor: String) {
        _uiState.value = _uiState.value.copy(percentualGorjeta = valor)
        limparResultado()
    }

    fun onNumeroPessoasChange(valor: String) {
        _uiState.value = _uiState.value.copy(numeroPessoas = valor)
        limparResultado()
    }

    fun calcular() {
        val estado = _uiState.value
        // Aceita ponto ou vírgula como separador decimal.
        val conta = estado.valorConta.trim().replace(',', '.').toDoubleOrNull()
        val percentual = estado.percentualGorjeta.trim().replace(',', '.').toDoubleOrNull()
        val pessoas = estado.numeroPessoas.trim().toIntOrNull()

        val erro = when {
            conta == null || percentual == null || pessoas == null ->
                "Preencha todos os campos com valores válidos."
            !conta.isFinite() || !percentual.isFinite() ->
                "Preencha todos os campos com valores válidos."
            conta <= 0 -> "O valor da conta deve ser maior que zero."
            pessoas < 1 -> "Informe ao menos 1 pessoa."
            percentual < 0 -> "O percentual de gorjeta não pode ser negativo."
            else -> null
        }
        if (erro != null) {
            limparResultado(erro)
            return
        }

        val gorjeta = conta!! * (percentual!! / 100)
        val total = conta + gorjeta
        if (!gorjeta.isFinite() || !total.isFinite()) {
            limparResultado("Preencha todos os campos com valores válidos.")
            return
        }
        _uiState.value = _uiState.value.copy(
            valorGorjeta = gorjeta,
            valorTotal = total,
            valorPorPessoa = total / pessoas!!,
            mensagemErro = null
        )
    }

    // Remove resultados antigos ao editar ou falhar na validação.
    private fun limparResultado(erro: String? = null) {
        _uiState.value = _uiState.value.copy(
            valorGorjeta = null,
            valorTotal = null,
            valorPorPessoa = null,
            mensagemErro = erro
        )
    }
}
