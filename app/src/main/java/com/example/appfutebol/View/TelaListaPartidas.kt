@Composable
fun TelaListaPartidas(
    partidas: List<Partida>,
    onPartidaSelecionada: (Partida) -> Unit,
    onAdicionarPartida: () -> Unit
) {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Button(
            onClick = onAdicionarPartida,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Adicionar Nova Partida")
        }
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(partidas) { partida ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                        .clickable { onPartidaSelecionada(partida) },
                    elevation = 4.dp
                ) {
                    Column(Modifier.padding(16.dp)) {
                        Text(text = "Adversário: ${partida.adversario}", style = MaterialTheme.typography.h6)
                        Text(text = "Data: ${partida.data}", style = MaterialTheme.typography.body2)
                        Text(text = "Resultado: ${partida.resultado}", style = MaterialTheme.typography.body2)
                    }
                }
            }
        }
    }
}
