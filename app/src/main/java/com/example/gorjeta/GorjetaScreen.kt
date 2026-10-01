package com.example.gorjeta

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import java.util.Locale

@Composable
fun GorjetaScreen(viewModel: GorjetaViewModel = viewModel()) {
    val estado by viewModel.uiState.collectAsStateWithLifecycle()
    Column(
        modifier = Modifier.fillMaxSize().safeDrawingPadding().imePadding()
            .verticalScroll(rememberScrollState()).padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Calculadora de Gorjeta", style = MaterialTheme.typography.headlineMedium)
        OutlinedTextField(
            value = estado.valorConta,
            onValueChange = viewModel::onValorContaChange,
            label = { Text("Valor da conta (R$)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = estado.percentualGorjeta,
            onValueChange = viewModel::onPercentualGorjetaChange,
            label = { Text("Percentual de gorjeta (%)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = estado.numeroPessoas,
            onValueChange = viewModel::onNumeroPessoasChange,
            label = { Text("Número de pessoas") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        Button(onClick = viewModel::calcular, modifier = Modifier.fillMaxWidth()) {
            Text("Calcular")
        }
        estado.mensagemErro?.let {
            Text(it, color = MaterialTheme.colorScheme.error)
        }
        estado.valorGorjeta?.let { Text("Gorjeta: ${formatarMoeda(it)}") }
        estado.valorTotal?.let { Text("Total: ${formatarMoeda(it)}") }
        estado.valorPorPessoa?.let { Text("Por pessoa: ${formatarMoeda(it)}") }
    }
}

// Formata apenas a exibição.
private fun formatarMoeda(valor: Double): String =
    String.format(Locale.forLanguageTag("pt-BR"), "R$ %.2f", valor)
