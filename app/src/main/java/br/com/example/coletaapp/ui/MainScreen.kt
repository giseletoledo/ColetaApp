package br.com.example.coletaapp.ui

import android.content.Context
import android.content.SharedPreferences
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Warning
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import br.com.example.coletaapp.ui.components.BottomNavigationBar
import br.com.example.coletaapp.ui.components.RoundedButton




@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: NavController) {

    val context = LocalContext.current
    //var notificationCount by remember { mutableStateOf(0) } // Change type to Int
    val sharedPreferences = context.getSharedPreferences("notification_prefs", Context.MODE_PRIVATE)

    // Estado para o número de notificações ativas
    val notificationCount = remember { mutableStateOf(getNotificationCountPreference(sharedPreferences)) }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Coleta App") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                ),
                actions = {
                    Box(modifier = Modifier.padding(8.dp)) {
                        IconButton(onClick = {
                            // Ação de clique no ícone de notificações (opcional)
                        }) {
                            BadgedBox(
                                badge = {
                                    if (notificationCount.value > 0) {
                                        Badge {
                                            Text(text = notificationCount.value.toString())
                                        }
                                    }
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Notifications,
                                    contentDescription = "Notificações",
                                    tint = Color.Yellow
                                )
                            }
                        }
                    }
                },
            )
        },
        bottomBar = {
            BottomNavigationBar(navController = navController)
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // Primeira linha de botões
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                RoundedButton(
                    icon = Icons.Default.LocationOn,
                    text = "Ecopontos",
                    onClick = { navController.navigate("addressList") }
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

            // Segunda linha de botões
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                RoundedButton(
                    icon = Icons.Default.Warning,
                    text = "Manuseio",
                    onClick = { /* Ação para Manuseio */ }
                )
                RoundedButton(
                    icon = Icons.Default.Info,
                    text = "Dicas",
                    onClick = { /* Ação para Dicas */ }
                )
                RoundedButton(
                    icon = Icons.Default.AccountBox,
                    text = "Campanha",
                    onClick = { /* Ação para Campanha */ }
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Seção "Você sabia?"
            Text(
                text = "Você sabia?",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(vertical = 16.dp)
            )

            // Card 1
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                Row(
                    modifier = Modifier
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically)
                {
                    Icon(
                        imageVector = Icons.Default.Create,
                        contentDescription = "lápis",
                        modifier = Modifier
                            .size(28.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = "Desde 2020, com a implementação do Decreto n° 10.388, os consumidores podem descartar medicamentos vencidos ou em desuso em farmácias que possuem pontos de coleta.",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }

            // Card 2
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                Row(
                    modifier = Modifier
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = "simbolo de informações",
                        modifier = Modifier
                            .size(28.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = "A reciclagem do óleo de cozinha usado permite a produção de sabão, biodiesel, tintas e outros produtos, contribuindo também para a redução da poluição ambiental.",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
        }
    }
}

@Composable
fun NotificationBadge(notificationCount: Int) {
    if (notificationCount > 0) {
        IconButton(onClick = { /* Handle notification click */ }) {
            Icon(Icons.Filled.Notifications, contentDescription = "Notifications")
            if (notificationCount > 9) {
                Text(
                    text = "+9", // Display "+" for high notification counts
                    color = Color.White,
                    fontSize = 10.sp,
                    modifier = Modifier
                        .size(16.dp)
                        .background(Color.Red, shape = CircleShape)
                        .padding(2.dp)
                )
            } else {
                Text(
                    text = notificationCount.toString(),
                    color = Color.White,
                    fontSize = 10.sp,
                    modifier = Modifier
                        .size(16.dp)
                        .background(Color.Red, shape = CircleShape)
                        .padding(4.dp)
                )
            }
        }
    } else {
        Icon(Icons.Filled.Notifications, contentDescription = "Notifications")
    }
}
// Função para obter a contagem de notificações
private fun getNotificationCountPreference(sharedPreferences: SharedPreferences): Int {
    return sharedPreferences.getInt("notification_count", 0)
}