package com.example.poli_app.ui.web

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.poli_app.R

class WebFragment : Fragment() {
    private lateinit var webView: WebView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflar el diseño del fragmento
        val view = inflater.inflate(R.layout.fragment_web, container, false)

        // Obtener referencia del EditText y el botón
        val editTextUrl: EditText = view.findViewById(R.id.editTextUrl)
        val buttonLoadWebPage: Button = view.findViewById(R.id.buttonLoadWebPage)

        //webView = findViewById(R.id.webView)

        // Configurar el clic del botón para cargar la página web
        buttonLoadWebPage.setOnClickListener {
            // Obtener la URL ingresada por el usuario
            val url = editTextUrl.text.toString()

            // Verificar si la URL no está vacía
            if (url.isNotEmpty()) {
                // Crear un intent implícito para abrir la URL en un navegador web
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                startActivity(intent)
                /*val url = editTextUrl.text.toString()
                webView.loadUrl(url)*/

            } else {
                // Mostrar un mensaje de error si la URL está vacía
                Toast.makeText(context, "Por favor, ingrese una URL", Toast.LENGTH_SHORT).show()
            }
        }

        return view
    }
}

/*class WebFragment : Fragment() {

}*/



/* private val viewModel: WebViewModel by viewModels()

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

}*/
