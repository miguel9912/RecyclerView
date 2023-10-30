package com.example.recyclerview

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerview.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    // Lista de plantas de ejemplo
    val plantList = listOf(
        Plant("Rosa", "Es una flor de la familia de las rosáceas, con tallo leñoso y espinoso, hojas compuestas, flores grandes y vistosas, generalmente de color rojo, blanco o amarillo, y fruto en forma de baya.", "Rosa x centifolia", R.drawable.rosa1, R.drawable.rosa2, listOf("Europa", "Asia", "África")),
        Plant("Orquídea", "Es una planta herbácea de la familia de las orquidáceas, con tallo corto o alargado, hojas alternas y flores muy vistosas y variadas, que crece en climas tropicales o templados.", "Orchidaceae", R.drawable.orquidea1, R.drawable.orquidea2, listOf("América", "Asia", "Oceanía")),
        Plant("Girasol", "Es una planta herbácea de la familia de las compuestas, con tallo alto y grueso, hojas grandes y ásperas, y flores amarillas en forma de disco que giran siguiendo la dirección del sol.", "Helianthus annuus", R.drawable.girasol1, R.drawable.girasol2, listOf("América del Norte", "América del Sur"))
    )

    // Índice del elemento seleccionado
    var selectedIndex = -1

    // Adaptador de la RecyclerView
    lateinit var adapter: PlantAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)

        // Inicializar el adaptador con la lista de plantas y un listener para el botón de detalle
        adapter = PlantAdapter(plantList) { position ->
            // Ir al detalle del elemento en la posición indicada
            goToDetail(position)
        }

        // Configurar la RecyclerView con el adaptador y un layout manager lineal
        recycler_view.adapter = adapter
        recycler_view.layoutManager = LinearLayoutManager(this)

        // Configurar el botón general de detalle
        detail_button.setOnClickListener {
            // Comprobar si hay algún elemento seleccionado
            if (selectedIndex != -1) {
                // Ir al detalle del elemento seleccionado
                goToDetail(selectedIndex)
            } else {
                // Mostrar un mensaje de error
                Toast.makeText(this, "No hay ningún elemento seleccionado", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Método para ir al detalle de un elemento
    fun goToDetail(position: Int) {
        // Crear un intent para iniciar la Activity 2
        val intent = Intent(this, DetailActivity::class.java)
        // Pasar el elemento seleccionado como extra
        intent.putExtra("plant", plantList[position])
        // Iniciar la Activity 2
        startActivity(intent)
    }

    // Clase interna para el adaptador de la RecyclerView
    inner class PlantAdapter(
        val plantList: List<Plant>, // Lista de plantas a mostrar
        val detailClickListener: (Int) -> Unit // Listener para el botón de detalle
    ) : RecyclerView.Adapter<PlantAdapter.PlantViewHolder>() {

        // Clase interna para el view holder de cada elemento
        inner class PlantViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            // Referencias a los views del layout del elemento
            val plantImage: ImageView = itemView.findViewById(R.id.plant_image)
            val plantName: TextView = itemView.findViewById(R.id.plant_name)
            val detailButton: Button = itemView.findViewById(R.id.detail_button)
            val cardView: CardView = itemView.findViewById(R.id.card_view)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlantViewHolder {
            // Inflar el layout del elemento
            val view = LayoutInflater.from(parent.context).inflate(R.layout.plant_item, parent, false)
            // Crear y devolver el view holder
            return PlantViewHolder(view)
        }

        override fun onBindViewHolder(holder: PlantViewHolder, position: Int) {
            // Obtener el elemento en la posición indicada
            val plant = plantList[position]
            // Asignar los datos del elemento a los views del view holder
            holder.plantImage.setImageResource(plant.image1)
            holder.plantName.text = plant.name

            // Configurar el listener para el botón de detalle
            holder.detailButton.setOnClickListener {
                // Invocar al listener del adaptador pasando la posición del elemento
                detailClickListener(position)
            }

            // Configurar el listener para el card view
            holder.cardView.setOnClickListener {
                // Comprobar si el elemento está seleccionado o no
                if (selectedIndex == position) {
                    // Desmarcar el elemento
                    selectedIndex = -1
                    holder.cardView.setCardBackgroundColor(Color.WHITE)
                } else {
                    // Marcar el elemento y desmarcar el anterior si lo hay
                    if (selectedIndex != -1) {
                        notifyItemChanged(selectedIndex)
                    }
                    selectedIndex = position
                    holder.cardView.setCardBackgroundColor(Color.LTGRAY)
                }
            }

            // Establecer el color del card view según si el elemento está seleccionado o no
            if (selectedIndex == position) {
                holder.cardView.setCardBackgroundColor(Color.LTGRAY)
            } else {
                holder.cardView.setCardBackgroundColor(Color.WHITE)
            }
        }

        override fun getItemCount(): Int {
            // Devolver el tamaño de la lista de plantas
            return plantList.size
        }
    }
}