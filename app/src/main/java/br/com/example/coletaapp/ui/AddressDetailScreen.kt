package br.com.example.coletaapp.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import br.com.example.coletaapp.model.Address
import br.com.example.coletaapp.model.Neighborhood
import br.com.example.coletaapp.model.Shift
import br.com.example.coletaapp.notifications.LocalNotificationManager
import br.com.example.coletaapp.ui.components.BottomNavigationBar
import androidx.compose.ui.platform.LocalContext
import android.content.Context
import android.content.SharedPreferences

import android.widget.Toast
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.ui.Alignment

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddressDetailScreen(navController: NavController, addressId: Int) {
    val context = LocalContext.current
    val sharedPreferences = context.getSharedPreferences("notification_prefs", Context.MODE_PRIVATE)

    // Estado para o número de notificações ativas
    val notificationCount = remember { mutableStateOf(getNotificationCountPreference(sharedPreferences)) }

    // Simulando dados de endereços
    val addresses = remember {
        listOf(
            Address(
                id = 1,
                state = "Ceará",
                city = "Fortaleza",
                neighborhoods = listOf(
                    Neighborhood(
                        name = "Aldeota",
                        shifts = listOf(
                            Shift(shiftType = "Diurno", time = "6h20", daysOfWeek = listOf("SEGUNDA", "QUARTA")),
                            Shift(shiftType = "Noturno", time = "18h00", daysOfWeek = listOf("TERÇA", "QUINTA"))
                        )
                    )
                )
            ),
            Address(
                id = 2,
                state = "São Paulo",
                city = "São Paulo",
                neighborhoods = listOf(
                    Neighborhood(
                        name = "Centro",
                        shifts = listOf(
                            Shift(shiftType = "Manhã", time = "8h00", daysOfWeek = listOf("SEGUNDA", "QUARTA")),
                            Shift(shiftType = "Tarde", time = "14h00", daysOfWeek = listOf("TERÇA", "QUINTA"))
                        )
                    )
                )
            )
        )
    }

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
                                    contentDescription = "Notificações"
                                )
                            }
                        }
                    }
                },
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
        selectedAddress?.let { address ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(16.dp)
            ) {
                Text(text = "Estado: ${address.state}")
                Text(text = "Cidade: ${address.city}")
                Spacer(modifier = Modifier.height(16.dp))

                LazyColumn {
                    items(address.neighborhoods) { neighborhood ->
                        Column(modifier = Modifier.padding(vertical = 8.dp)) {
                            Text(
                                text = "Bairro: ${neighborhood.name}",
                                style = MaterialTheme.typography.titleMedium
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(text = "Turnos:", style = MaterialTheme.typography.titleSmall)

                            neighborhood.shifts.forEach { shift ->
                                val shiftChecked = remember {
                                    mutableStateOf(getNotificationPreference(sharedPreferences, "${address.city}_${shift.shiftType}"))
                                }

                                Card(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 4.dp),
                                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                                ) {
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(16.dp),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Column {
                                            Text(text = "Turno: ${shift.shiftType}")
                                            Text(text = "Horário: ${shift.time}")
                                        }

                                        Switch(
                                            checked = shiftChecked.value,
                                            onCheckedChange = { checked ->
                                                shiftChecked.value = checked
                                                notificationCount.value = if (checked) {
                                                    notificationCount.value + 1
                                                } else {
                                                    notificationCount.value - 1
                                                }
                                                saveNotificationPreference(sharedPreferences, "${address.city}_${shift.shiftType}", checked)
                                                saveNotificationCountPreference(sharedPreferences, notificationCount.value)

                                                if (checked) {
                                                    LocalNotificationManager.scheduleNotification(
                                                        address.city, shift.shiftType, shift.time, shift.daysOfWeek, context
                                                    )
                                                    Toast.makeText(
                                                        context, "Notificação para ${shift.shiftType} agendada", Toast.LENGTH_SHORT
                                                    ).show()
                                                } else {
                                                    LocalNotificationManager.cancelNotification(address.city, context)
                                                    Toast.makeText(
                                                        context, "Notificação para ${shift.shiftType} cancelada", Toast.LENGTH_SHORT
                                                    ).show()
                                                }
                                            })
                                    }
                                }
                            }
                            Spacer(modifier = Modifier.height(16.dp))
                        }
                    }
                }
            }
        }
    } ?: run {
        Text(text = "Endereço não encontrado", modifier = Modifier.padding(16.dp))
    }
}


// Função para salvar a preferência de notificação
private fun saveNotificationPreference(sharedPreferences: SharedPreferences, key: String, isEnabled: Boolean) {
    sharedPreferences.edit().putBoolean(key, isEnabled).apply()
}

// Função para salvar a contagem de notificações
private fun saveNotificationCountPreference(sharedPreferences: SharedPreferences, count: Int) {
    sharedPreferences.edit().putInt("notification_count", count).apply()
}

// Função para obter a contagem de notificações
private fun getNotificationCountPreference(sharedPreferences: SharedPreferences): Int {
    return sharedPreferences.getInt("notification_count", 0)
}

// Função para obter a preferência de notificação
private fun getNotificationPreference(sharedPreferences: SharedPreferences, key: String): Boolean {
    return sharedPreferences.getBoolean(key, false)
}
