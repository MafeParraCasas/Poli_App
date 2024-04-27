package com.example.poli_app.ui.video


import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.MediaController
import android.widget.VideoView
import androidx.fragment.app.Fragment
import com.example.poli_app.R

class VideoFragment : Fragment() {

    private lateinit var videoView: VideoView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_video, container, false)

        // Obtener el VideoView del layout
        videoView = view.findViewById(R.id.video)

        // Especificar la URI del recurso de video en res/raw
        val videoUri = Uri.parse("android.resource://" + requireActivity().packageName + "/" + R.raw.cancion)

        // Configurar el VideoView para reproducir el video
        videoView.setVideoURI(videoUri)

        // Configurar los controles de reproducción de medios
        val mediaController = MediaController(requireContext())
        mediaController.setAnchorView(videoView)
        videoView.setMediaController(mediaController)

        // Iniciar la reproducción del video
        videoView.start()

        return view
    }
}
