package br.com.example.coletaapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import br.com.example.coletaapp.ui.AddressListScreen
import br.com.example.coletaapp.ui.MainScreen
import br.com.example.coletaapp.ui.AddressDetailScreen

@Composable
fun AppNavigation() {
    // Criação do NavController
    val navController = rememberNavController()

    // Configuração da navegação
    NavHost(navController = navController, startDestination = "mainScreen") {
        composable("mainScreen") {
            // Passando o navController para a MainScreen
            MainScreen(navController = navController)
        }
        composable("addressList") {
            AddressListScreen(navController = navController)
        }
        composable("addressDetail/{addressId}") { backStackEntry ->
            val addressId = backStackEntry.arguments?.getString("addressId")?.toInt() ?: -1
            AddressDetailScreen(navController = navController, addressId = addressId)
        }
    }
}
