package br.com.example.coletaapp

import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import br.com.example.coletaapp.navigation.AppNavigation
import br.com.example.coletaapp.notifications.LocalNotificationManager
import br.com.example.coletaapp.ui.theme.ColetaAppTheme


class MainActivity : ComponentActivity() {

    // Novo método para gerenciar a solicitação de permissões
    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            // Permissão concedida, inicialize as notificações
            LocalNotificationManager.createNotificationChannel(this)
        } else {
            // Permissão negada, mostre uma mensagem ou lide com a recusa
            Toast.makeText(this, "Permissão para notificações negada.", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ColetaAppTheme {
                // Verifica se a permissão já foi concedida
                if (ContextCompat.checkSelfPermission(
                        this,
                        android.Manifest.permission.POST_NOTIFICATIONS
                    ) == PackageManager.PERMISSION_GRANTED
                ) {
                    // Se já concedida, inicialize o canal de notificações
                    LocalNotificationManager.createNotificationChannel(this)
                } else {
                    // Caso contrário, solicite a permissão usando a nova API
                    requestPermissionLauncher.launch(android.Manifest.permission.POST_NOTIFICATIONS)
                }

                AppNavigation()  // Inicia a navegação
            }
        }
    }
}

