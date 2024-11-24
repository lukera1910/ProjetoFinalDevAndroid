package com.example.appfutebol.View

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

class PartidasActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TelaPartidas(
                onAdicionarPartida = { /* Adicionar nova partida */ },
                onVerDesempenho = { partida -> /* Abrir detalhes da partida */ }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TelaPartidas(
    onAdicionarPartida: () -> Unit,
    onVerDesempenho: (String) -> Unit
) {
    val partidas = remember {
        mutableStateListOf(
            "Partida 1: 01/11/2024 - Adversário X",
            "Partida 2: 05/11/2024 - Adversário Y"
        )
    }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Partidas") }) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            LazyColumn {
                items(partidas.size) { index ->
                    val partida = partidas[index]
                    ListItem(
                        headlineContent = { Text(partida) },
                        trailingContent = {
                            Button(onClick = { onVerDesempenho(partida) }) {
                                Text("Desempenho")
                            }
                        }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = onAdicionarPartida,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Adicionar Partida")
            }
        }
    }
}