# Atividade 02: Uso de ViewModel e StateFlow

Projeto Android da atividade **Calculadora de Gorjeta e Divisão de Conta**.

## Executar

1. Abra esta pasta no Android Studio.
2. Use JDK 17 ou 21 e instale o Android SDK 35.
3. Aguarde a sincronização do Gradle.
4. Execute o módulo `app` em um dispositivo ou emulador Android 7.0 ou superior.
5. No menu inicial, toque em **Calculadora de Gorjeta**.

## Organização

- `GorjetaUiState.kt`: estado imutável da tela.
- `GorjetaViewModel.kt`: entradas, validações e cálculos.
- `GorjetaScreen.kt`: campos, botão e exibição dos resultados.
- `MainActivity.kt`: menu inicial e NavHost com rotas `@Serializable`.
- `GorjetaViewModelTest.kt`: testes das regras e mudanças de estado.

## Uso

Informe conta, percentual e número de pessoas. Toque em **Calcular**.
Os campos decimais aceitam ponto ou vírgula. O número de pessoas deve ser inteiro.
Os resultados aparecem em reais, com duas casas decimais. Editar um campo limpa o resultado anterior.

Exemplo: conta de R$ 100, gorjeta de 10% e 2 pessoas resulta em R$ 10 de gorjeta,
R$ 110 no total e R$ 55 por pessoa.

A tela apenas apresenta o estado e envia eventos. A validação e os cálculos ficam no ViewModel.
O estado usa `MutableStateFlow` privado, `StateFlow` público e atualizações com `.copy()`.
A tela observa o estado com `collectAsStateWithLifecycle()`.

## Validar

No Windows:

```powershell
.\gradlew.bat testDebugUnitTest assembleDebug lintDebug
```

No Linux/macOS:

```sh
chmod +x gradlew
./gradlew testDebugUnitTest assembleDebug lintDebug
```

O APK é gerado em `app/build/outputs/apk/debug/app-debug.apk`.
A entrega compactada deve excluir `.git`, `.gradle`, `build` e `local.properties`.

## Verificação realizada

- 12 testes unitários aprovados.
- APK debug compilado.
- Android Lint concluído sem erros; há avisos de versões mais recentes de dependências.
- Interface ainda não testada em dispositivo ou emulador.
