@Composable
fun TelaAdicionarPartida(onSalvarPartida: (Partida) -> Unit) {
    var data by remember { mutableStateOf("") }
    var adversario by remember { mutableStateOf("") }
    var resultado by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        TextField(value = data, onValueChange = { data = it }, label = { Text("Data") })
        TextField(value = adversario, onValueChange = { adversario = it }, label = { Text("Adversário") })
        TextField(value = resultado, onValueChange = { resultado = it }, label = { Text("Resultado") })

        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                onSalvarPartida(Partida(data = data, adversario = adversario, resultado = resultado))
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Salvar Partida")
        }
    }
}
