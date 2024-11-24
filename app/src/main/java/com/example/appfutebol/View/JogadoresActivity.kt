package com.example.projetofinaldevandroid

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.input.TextFieldValue
import com.example.appfutebol.View.PartidasActivity

class JogadoresActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TelaJogadores(
                onAdicionarJogador = { /* Abrir cadastro de jogador */ },
                onPartidasClick = {
                    val intent = Intent(this, PartidasActivity::class.java)
                    startActivity(intent)
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TelaJogadores(
    onAdicionarJogador: () -> Unit,
    onPartidasClick: () -> Unit
) {
    var busca by remember { mutableStateOf(TextFieldValue("")) }
    val jogadores = remember { mutableStateListOf("Jogador 1 - Atacante", "Jogador 2 - Goleiro") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Jogadores") },
                actions = {
                    Button(onClick = onPartidasClick) {
                        Text("Partidas")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            TextField(
                value = busca,
                onValueChange = { busca = it },
                label = { Text("Buscar jogador") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))

            jogadores.filter { it.contains(busca.text, ignoreCase = true) }.forEach { jogador ->
                ListItem(
                    headlineContent = { Text(jogador) },
                    trailingContent = {
                        Row {
                            Button(onClick = { /* Editar */ }) { Text("Editar") }
                            Spacer(modifier = Modifier.width(8.dp))
                            Button(onClick = { /* Excluir */ }) { Text("Excluir") }
                        }
                    }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = onAdicionarJogador,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Adicionar Jogador")
            }
        }
    }
}