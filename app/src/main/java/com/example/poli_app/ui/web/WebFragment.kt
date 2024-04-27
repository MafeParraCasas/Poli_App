package com.example.poli_app.ui.web

import android.content.Intent
import android.net.Uri
import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.poli_app.R




class WebFragment : Fragment() {

    companion object {
        fun newInstance() = WebFragment()
    }

    private val viewModel: WebViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_web, container, false)
    }



    class MainActivity : AppCompatActivity() {
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_main)
        }

        fun loadWebPage(view: View) {
            // Obtiene la referencia del EditText
            val editTextUrl: EditText = findViewById(R.id.editTextUrl)

            // Obtiene la URL ingresada por el usuario
            val url = editTextUrl.text.toString()

            // Verifica si la URL no está vacía
            if (url.isNotEmpty()) {
                // Crea un intent implícito para abrir la URL en un navegador web
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                startActivity(intent)
            }
        }
    }

}