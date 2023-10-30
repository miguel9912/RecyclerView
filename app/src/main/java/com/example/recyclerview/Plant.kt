package com.example.recyclerview

import java.io.Serializable

data class Plant(
    val name: String, // Nombre común de la planta
    val description: String, // Descripción de la planta
    val scientificName: String, // Nombre científico de la planta
    val image1: Int, // Recurso de la primera imagen de la planta
    val image2: Int, // Recurso de la segunda imagen de la planta
    val zones: List<String> // Lista de zonas predominantes de la planta
) : Serializable
