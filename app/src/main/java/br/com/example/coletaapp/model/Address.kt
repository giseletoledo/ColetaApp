package br.com.example.coletaapp.model


data class Address(
    val id: Int,
    val state: String,
    val city: String,
    val neighborhoods: List<Neighborhood>
)

