package com.example.recyclerview

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity


class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        // Obtener la planta pasada como extra desde la Activity 1
        val plant = intent.getSerializableExtra("plant") as Plant

        // Asignar los datos de la planta a los views del layout del detalle
        plant_image1.setImageResource(plant.image1)
        plant_image2.setImageResource(plant.image2)
        plant_name.text = plant.name
        plant_description.text = plant.description
        plant_scientific_name.text = plant.scientificName
        plant_zones.text = plant.zones.joinToString(", ")
    }
}