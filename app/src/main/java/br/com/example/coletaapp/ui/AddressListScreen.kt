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
import br.com.example.coletaapp.model.Address
import br.com.example.coletaapp.ui.components.BottomNavigationBar


@Composable
fun AddressListScreen(navController: NavController) {
    // Simulando dados de endereços
    val addresses = remember {
        listOf(
            Address(id = 1, state = "Ceará", city = "Fortaleza", neighborhoods = emptyList()),
            Address(id = 2, state = "São Paulo", city = "São Paulo", neighborhoods = emptyList())
            // Adicione mais endereços conforme necessário
        )
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
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            items(addresses) { address ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .clickable {
                            navController.navigate("addressDetail/${address.id}")
                        },
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "${address.state}, ${address.city}")
                }
            }
        }
    }
}
