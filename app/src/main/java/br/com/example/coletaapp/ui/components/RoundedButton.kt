package br.com.example.coletaapp.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun RoundedButton(
    icon: ImageVector,
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    buttonColor: Color = MaterialTheme.colorScheme.primary,
    contentColor: Color = MaterialTheme.colorScheme.onPrimary,
    iconSize: Int = 62 // Novo parâmetro para definir o tamanho do ícone
) {
    Column(
        modifier = modifier
            .padding(6.dp)
            .width(120.dp),  // Largura do botão quadrado
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Alterando o shape para RoundedCornerShape para cantos arredondados
        Button(
            onClick = onClick,
            shape = RoundedCornerShape(12.dp),  // Forma quadrada com cantos arredondados
            colors = ButtonDefaults.buttonColors(
                containerColor = buttonColor,
                contentColor = contentColor
            ),
            modifier = Modifier
                .size(96.dp) // Aumentando o tamanho do botão quadrado
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxSize()
            ) {
                Icon(icon, contentDescription = null, tint = Color.White) // Cor do ícone
                Text(text, color = Color.White, fontSize = 12.sp) // Cor do texto
            }
        }
        Spacer(modifier = Modifier.height(8.dp))

    }
}
