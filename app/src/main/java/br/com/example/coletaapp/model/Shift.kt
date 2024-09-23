package br.com.example.coletaapp.model

data class Shift(
    val shiftType: String,  // Ex: "Manhã", "Tarde"
    val time: String,       // Ex: "6h20", "14h00"
    var notificationEnabled: Boolean = false, // Estado da notificação
    val daysOfWeek: List<String> // Ex: ["SEGUNDA", "QUARTA"]
)

