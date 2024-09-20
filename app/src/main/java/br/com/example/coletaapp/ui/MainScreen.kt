package br.com.example.coletaapp.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import br.com.example.coletaapp.ui.components.BottomNavigationBar
import br.com.example.coletaapp.ui.components.RoundedButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Coleta App") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary, // Cor de fundo da TopAppBar
                    titleContentColor = MaterialTheme.colorScheme.onPrimary // Cor do título
                )
            )
        },
        bottomBar = {
            BottomNavigationBar(navController = navController)  // Passando o navController
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // Seção de botões
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                RoundedButton(
                    icon = Icons.Default.LocationOn,
                    text = "Ecopontos",
                    onClick = { navController.navigate("addressList") } // navegação
                )
                RoundedButton(
                    icon = Icons.Default.Refresh,
                    text = "Materiais",
                    onClick = { /* Ação para Materiais */ }
                )
                RoundedButton(
                    icon = Icons.Default.Delete,
                    text = "Nova coleta",
                    onClick = { /* Ação para Nova coleta */ }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Outras seções de botões...
        }
    }
}
