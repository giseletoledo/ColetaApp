package br.com.example.coletaapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import br.com.example.coletaapp.navigation.AppNavigation
import br.com.example.coletaapp.ui.theme.ColetaAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ColetaAppTheme {
                AppNavigation()  // Inicia a navegação
            }
        }
    }
}
