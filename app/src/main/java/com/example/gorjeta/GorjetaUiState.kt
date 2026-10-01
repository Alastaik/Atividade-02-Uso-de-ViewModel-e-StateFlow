package com.example.gorjeta

data class GorjetaUiState(
    val valorConta: String = "",
    val percentualGorjeta: String = "",
    val numeroPessoas: String = "",
    val valorGorjeta: Double? = null,
    val valorTotal: Double? = null,
    val valorPorPessoa: Double? = null,
    val mensagemErro: String? = null
)
