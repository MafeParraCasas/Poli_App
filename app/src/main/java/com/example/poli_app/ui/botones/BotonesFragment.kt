package com.example.poli_app.ui.botones

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.poli_app.R
import kotlin.random.Random

class BotonesFragment : Fragment() {

    companion object {
        fun newInstance() = BotonesFragment()
    }

    private val viewModel: BotonesViewModel by viewModels()

    private var ultimoNumeroDado: Int = 0
    private lateinit var inputNumero: EditText
    private lateinit var botonApostar: Button
    private lateinit var textViewResultado: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    private lateinit var imagenDado: ImageView

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_botones, container, false)

        inputNumero = view.findViewById(R.id.inputNumero)
        botonApostar = view.findViewById(R.id.botonApostar)
        textViewResultado = view.findViewById(R.id.textViewResultado)
        imagenDado = view.findViewById(R.id.imagenDado)

        botonApostar.setOnClickListener {
            apostar()
        }

        return view
    }

    private fun apostar() {
        val numeroIngresado = inputNumero.text.toString().toIntOrNull() ?: 0
        val numeroDado = lanzarDado()

        actualizarImagenDado(numeroDado)

        if (numeroIngresado == numeroDado) {
            textViewResultado.text = "¡Ganaste!"
        } else {
            textViewResultado.text = "Perdiste. El número del dado fue: $numeroDado"
        }

        inputNumero.text.clear()
        ultimoNumeroDado = numeroDado
    }

    private fun lanzarDado(): Int {
        return (1..6).random()
    }

    @SuppressLint("DiscouragedApi")
    private fun actualizarImagenDado(numeroDado: Int) {
        val idImagenDado = resources.getIdentifier("dado_$numeroDado", "drawable", context?.packageName)
        imagenDado.setImageResource(idImagenDado)
    }
}