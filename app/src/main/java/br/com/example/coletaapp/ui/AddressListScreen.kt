@file:OptIn(ExperimentalMaterial3Api::class)

package br.com.example.coletaapp.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import br.com.example.coletaapp.ui.components.BottomNavigationBar

@Composable
fun AddressListScreen(navController: NavController) {
    val addresses = listOf(
        Address(1, "Ceará", "Fortaleza", "Aldeota", "6h20", "SEGUNDA, QUARTA E SEXTA", "Diurno"),
        Address(2, "São Paulo", "São Paulo", "Centro", "8h00", "TERÇA E QUINTA", "Noturno")
    )

    // State to hold the search query
    var searchQuery by remember { mutableStateOf("") }

    // Filtered addresses based on the search query
    val filteredAddresses = addresses.filter {
        it.city.contains(searchQuery, ignoreCase = true) ||
                it.state.contains(searchQuery, ignoreCase = true)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Lista de Endereços") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        },
        bottomBar = {
            BottomNavigationBar(navController = navController)
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            // Search TextField
            TextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                label = { Text("Buscar por cidade ou estado") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            // LazyColumn to display filtered addresses
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(filteredAddresses) { address ->
                    Text(
                        text = "${address.state}, ${address.city}",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                            .clickable {
                                navController.navigate("addressDetail/${address.id}")
                            }
                    )
                }
            }
        }
    }
}
