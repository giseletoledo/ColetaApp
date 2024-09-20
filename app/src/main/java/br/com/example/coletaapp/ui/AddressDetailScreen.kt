package br.com.example.coletaapp.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import br.com.example.coletaapp.ui.components.BottomNavigationBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddressDetailScreen(navController: NavController, addressId: Int) {
    val addresses = listOf(
        Address(1, "Ceará", "Fortaleza", "Aldeota", "6h20", "SEGUNDA, QUARTA E SEXTA", "Diurno"),
        Address(2, "São Paulo", "São Paulo", "Centro", "8h00", "TERÇA E QUINTA", "Noturno")
    )

    val selectedAddress = addresses.firstOrNull { it.id == addressId }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Detalhes do Endereço") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Voltar")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary, // Cor primária do tema
                    titleContentColor = MaterialTheme.colorScheme.onPrimary // Cor do texto sobre a cor primária
                )
            )
        },
        bottomBar = {
            BottomNavigationBar(navController = navController)  // Barra de navegação sempre presente
        }
    ) { innerPadding ->
        selectedAddress?.let { address ->
            Column(modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)) {
                Text(text = "Cidade: ${address.city}")
                Text(text = "Estado: ${address.state}")
                Text(text = "Bairro: ${address.neighborhood}")
                Text(text = "Turno: ${address.shift}")
                Text(text = "Horário: A partir das ${address.time}")
                Text(text = "Dias da Semana: ${address.daysOfWeek}")
            }
        } ?: run {
            Text(text = "Endereço não encontrado", modifier = Modifier.padding(16.dp))
        }
    }
}
