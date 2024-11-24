package com.example.projetofinaldevandroid

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.input.TextFieldValue
import com.example.appfutebol.Database.AppDatabase
import com.example.appfutebol.View.PartidasActivity
import com.example.appfutebol.ViewModel.JogadorViewModel
import com.example.appfutebol.ViewModelFactory.JogadorViewModelFactory

class JogadoresActivity : ComponentActivity() {
    private val jogadorViewModel: JogadorViewModel by viewModels {
        val dao = AppDatabase.getDatabase(applicationContext).jogadorDao()
        JogadorViewModelFactory(dao)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TelaJogadores(
                jogadorViewModel = jogadorViewModel,
                onAdicionarJogador = { /*jogadorViewModel.adicionarJogador()*/ },
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
    jogadorViewModel: JogadorViewModel,
    onAdicionarJogador: () -> Unit,
    onPartidasClick: () -> Unit
) {
    var busca by remember { mutableStateOf(TextFieldValue("")) }

    var nome by remember { mutableStateOf("") }
    var posicao by remember { mutableStateOf("") }
    var numero by remember { mutableIntStateOf(1) }
    var pernaBoa by remember { mutableStateOf("") }
    var altura by remember { mutableDoubleStateOf(0.0) }
    var idade by remember { mutableIntStateOf(16) }
    var nacionalidade by remember { mutableStateOf("") }

    val jogadores by jogadorViewModel.listaJogadores

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

            val jogador = jogadorViewModel.buscarJogadorPorNome(busca.text)
            ListItem(
                headlineContent = { Text(text = jogador!!.nome) },
                trailingContent = {
                    Row {
                        Button(onClick = { /*TODO*/ }) { Text(text = "Editar")}
                        Spacer(modifier = Modifier.width(8.dp))
                        Button(onClick = { /*TODO*/ }) { Text(text = "Excluir")}
                    }
                }
            )

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