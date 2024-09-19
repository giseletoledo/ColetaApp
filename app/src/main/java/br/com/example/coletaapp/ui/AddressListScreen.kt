@file:OptIn(ExperimentalMaterial3Api::class)

package br.com.example.coletaapp.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import br.com.example.coletaapp.ui.theme.ColetaAppTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun AddressListScreen(navController: NavController) {
    val addresses = listOf(
        Address(1, "Ceará", "Fortaleza", "Aldeota", "6h20", "SEGUNDA, QUARTA E SEXTA", "Diurno"),
        Address(2, "São Paulo", "São Paulo", "Centro", "8h00", "TERÇA E QUINTA", "Noturno")
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Lista de Endereços") },
                // Definindo cores personalizadas para o TopAppBar
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary, // Cor primária do tema
                    titleContentColor = MaterialTheme.colorScheme.onPrimary // Cor do texto sobre a cor primária
            )
            )
        }
    ) { innerPadding ->
        LazyColumn(modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)) {
            items(addresses) { address ->
                Text(
                    text = "${address.state}, ${address.city}",
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                        .clickable {
                            navController.navigate("addressDetail/${address.id}")
                        }
                )
            }
        }
    }
}
