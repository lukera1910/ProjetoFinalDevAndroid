package com.example.appfutebol.View

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.appfutebol.Database.AppDatabase
import com.example.appfutebol.Model.Jogador
import com.example.appfutebol.ViewModel.JogadorViewModel
import com.example.appfutebol.ViewModelFactory.JogadorViewModelFactory

class JogadoresActivity : ComponentActivity() {
    private lateinit var viewModel: JogadorViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val dao = AppDatabase.getDatabase(this).jogadorDao()
        viewModel = ViewModelProvider(this, JogadorViewModelFactory(dao))[JogadorViewModel::class.java]
        setContent {
            TelaJogadores(viewModel)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TelaJogadores(viewModel: JogadorViewModel = viewModel()) {
    val jogadores by remember { viewModel.listaJogadores }
    var busca by remember { mutableStateOf("") }

    var exibirAdicionarDialog by remember { mutableStateOf(false) }
    if (exibirAdicionarDialog) {
        AdicionarJogadorDialog(
            onDismiss = { exibirAdicionarDialog = false },
            onSalvar = { nome, posicao, numero, pernaBoa, altura, idade, nacionalidade ->
                viewModel.adicionarJogador(nome, posicao, numero, pernaBoa, altura, idade, nacionalidade)
            }
        )
    }

    var exibirEditarDialog by remember { mutableStateOf(false) }
    var jogadorParaEditar by remember { mutableStateOf<Jogador?>(null) }

    if (exibirEditarDialog && jogadorParaEditar != null) {
        EditarJogadorDialog(
            jogador = jogadorParaEditar!!,
            onDismiss = { exibirEditarDialog = false },
            onSalvar = { id, nome, posicao, numero, pernaBoa, altura, idade, nacionalidade ->
                viewModel.atualizarJogador(id, nome, posicao, numero, pernaBoa, altura, idade, nacionalidade)
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Jogadores") })
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
                label = { Text("Buscar Jogador") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn {
                items(jogadores.filter {
                    it.nome.contains(busca, ignoreCase = true) || it.posicao.contains(busca, ignoreCase = true)
                }) { jogador ->
                    JogadorItem(
                        jogador = jogador,
                        onEditar = {
                            jogadorParaEditar = jogador
                            exibirEditarDialog = true
                        },
                        onExcluir = { viewModel.excluirJogador(jogador) }
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = { exibirAdicionarDialog = true }) {
                Text("Adicionar Jogador")
            }

            Spacer(modifier = Modifier.height(16.dp))
            val context = LocalContext.current
            Button(onClick = {
                val intent = Intent(context, PartidasActivity::class.java)
                context.startActivity(intent)
            }) {
                Text("Partidas")
            }
        }
    }
}

@Composable
fun JogadorItem(
    jogador: Jogador,
    onEditar: () -> Unit,
    onExcluir: () -> Unit
) {
    ListItem(
        headlineContent = { Text("${jogador.nome} - ${jogador.posicao}") },
        supportingContent = {
            Text("Número: ${jogador.numero}, Altura: ${jogador.altura}m, Idade: ${jogador.idade} anos")
        },
        trailingContent = {
            Row {
                Button(onClick = onEditar) { Text("Editar") }
                Spacer(modifier = Modifier.width(8.dp))
                Button(onClick = onExcluir) { Text("Excluir") }
            }
        }
    )
}

@Composable
fun AdicionarJogadorDialog(
    onDismiss: () -> Unit,
    onSalvar: (nome: String, posicao: String, numero: Int, pernaBoa: String, altura: Double, idade: Int, nacionalidade: String) -> Unit
) {
    var nome by remember { mutableStateOf("") }
    var posicao by remember { mutableStateOf("") }
    var numero by remember { mutableStateOf("") }
    var pernaBoa by remember { mutableStateOf("") }
    var altura by remember { mutableStateOf("") }
    var idade by remember { mutableStateOf("") }
    var nacionalidade by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Adicionar Jogador") },
        text = {
            Column {
                TextField(value = nome, onValueChange = { nome = it }, label = { Text("Nome") })
                TextField(value = posicao, onValueChange = { posicao = it }, label = { Text("Posição") })
                TextField(value = numero, onValueChange = { numero = it }, label = { Text("Número") })
                TextField(value = pernaBoa, onValueChange = { pernaBoa = it }, label = { Text("Perna Boa") })
                TextField(value = altura, onValueChange = { altura = it }, label = { Text("Altura (m)") })
                TextField(value = idade, onValueChange = { idade = it }, label = { Text("Idade") })
                TextField(value = nacionalidade, onValueChange = { nacionalidade = it }, label = { Text("Nacionalidade") })
            }
        },
        confirmButton = {
            Button(onClick = {
                onSalvar(
                    nome, posicao, numero.toIntOrNull() ?: 0, pernaBoa, altura.toDoubleOrNull() ?: 0.0,
                    idade.toIntOrNull() ?: 0, nacionalidade
                )
                onDismiss()
            }) {
                Text("Salvar")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text("Cancelar")
            }
        }
    )
}

@Composable
fun EditarJogadorDialog(
    jogador: Jogador,
    onDismiss: () -> Unit,
    onSalvar: (id: Int, nome: String, posicao: String, numero: Int, pernaBoa: String, altura: Double, idade: Int, nacionalidade: String) -> Unit
) {
    var nome by remember { mutableStateOf(jogador.nome) }
    var posicao by remember { mutableStateOf(jogador.posicao) }
    var numero by remember { mutableStateOf(jogador.numero.toString()) }
    var pernaBoa by remember { mutableStateOf(jogador.pernaBoa) }
    var altura by remember { mutableStateOf(jogador.altura.toString()) }
    var idade by remember { mutableStateOf(jogador.idade.toString()) }
    var nacionalidade by remember { mutableStateOf(jogador.nacionalidade) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Editar Jogador") },
        text = {
            Column {
                TextField(value = nome, onValueChange = { nome = it }, label = { Text("Nome") })
                TextField(value = posicao, onValueChange = { posicao = it }, label = { Text("Posição") })
                TextField(value = numero, onValueChange = { numero = it }, label = { Text("Número") })
                TextField(value = pernaBoa, onValueChange = { pernaBoa = it }, label = { Text("Perna Boa") })
                TextField(value = altura, onValueChange = { altura = it }, label = { Text("Altura (m)") })
                TextField(value = idade, onValueChange = { idade = it }, label = { Text("Idade") })
                TextField(value = nacionalidade, onValueChange = { nacionalidade = it }, label = { Text("Nacionalidade") })
            }
        },
        confirmButton = {
            Button(onClick = {
                onSalvar(
                    jogador.id, nome, posicao, numero.toIntOrNull() ?: 0, pernaBoa,
                    altura.toDoubleOrNull() ?: 0.0, idade.toIntOrNull() ?: 0, nacionalidade
                )
                onDismiss()
            }) {
                Text("Salvar")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text("Cancelar")
            }
        }
    )
}