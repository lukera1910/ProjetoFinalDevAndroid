@Composable
fun TelaDetalhesPartida(
    partida: Partida,
    desempenhos: List<Desempenho>,
    onAdicionarDesempenho: (Desempenho) -> Unit
) {
    var jogador by remember { mutableStateOf("") }
    var gols by remember { mutableStateOf(0) }
    var assistencias by remember { mutableStateOf(0) }
    var cartoes by remember { mutableStateOf(0) }
    var nota by remember { mutableStateOf(0.0) }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(text = "Detalhes da Partida", style = MaterialTheme.typography.h5)
        Text(text = "Adversário: ${partida.adversario}")
        Text(text = "Resultado: ${partida.resultado}")
        Text(text = "Data: ${partida.data}")
        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(modifier = Modifier.weight(1f)) {
            items(desempenhos) { desempenho ->
                Text("Jogador: ${desempenho.idJogador} | Gols: ${desempenho.gols} | Nota: ${desempenho.nota}")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        TextField(value = jogador, onValueChange = { jogador = it }, label = { Text("Jogador") })
        Row {
            TextField(value = gols.toString(), onValueChange = { gols = it.toIntOrNull() ?: 0 }, label = { Text("Gols") })
            Spacer(modifier = Modifier.width(8.dp))
            TextField(value = assistencias.toString(), onValueChange = { assistencias = it.toIntOrNull() ?: 0 }, label = { Text("Assistências") })
        }
        Row {
            TextField(value = cartoes.toString(), onValueChange = { cartoes = it.toIntOrNull() ?: 0 }, label = { Text("Cartões") })
            Spacer(modifier = Modifier.width(8.dp))
            TextField(value = nota.toString(), onValueChange = { nota = it.toDoubleOrNull() ?: 0.0 }, label = { Text("Nota") })
        }
        Button(
            onClick = {
                onAdicionarDesempenho(
                    Desempenho(
                        idPartida = partida.id,
                        idJogador = jogador.toIntOrNull() ?: 0, // Substituir por ID real do jogador
                        gols = gols,
                        assists = assistencias,
                        numCartoesAmarelos = cartoes,
                        nota = nota,
                        minutosJogados = 90 // Ajustar dinamicamente se necessário
                    )
                )
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Registrar Desempenho")
        }
    }
}
