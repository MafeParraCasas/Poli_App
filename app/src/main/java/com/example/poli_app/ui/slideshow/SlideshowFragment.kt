package com.example.poli_app.ui.slideshow

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.poli_app.R
import com.google.android.material.button.MaterialButton

class SlideshowFragment : Fragment() {
    private val photoList = mutableListOf<Photo>()
    private val photoAdapter = PhotoAdapter(photoList)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return try {
            val view = inflater.inflate(R.layout.fragment_slideshow, container, false)

            val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
            recyclerView.layoutManager = LinearLayoutManager(context)
            recyclerView.adapter = photoAdapter

            if (photoList.isEmpty()) {
                AlertDialog.Builder(requireContext())
                    .setTitle("No hay fotos")
                    .setMessage("No hay fotos disponibles")
                    .setPositiveButton("Aceptar", null)
                    .show()
            }

            val addPhotoButton = view.findViewById<MaterialButton>(R.id.addPhotoButton)
            addPhotoButton.setOnClickListener {
                showAddPhotoDialog()
            }

            view
        } catch (e: Exception) {
            Log.e("SlideshowFragment", "Error en onCreateView: ${e.message}", e)
            null
        }
    }

    private fun showAddPhotoDialog() {
        val dialog = AddPhotoDialogFragment(photoList, photoAdapter)
        dialog.show(childFragmentManager, "AddPhotoDialog")
    }

    inner class PhotoAdapter(private val photoList: List<Photo>) : RecyclerView.Adapter<PhotoAdapter.PhotoViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_photo, parent, false)
            return PhotoViewHolder(view)
        }

        override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
            val photo = photoList[position]
            holder.bind(photo)
        }

        override fun getItemCount(): Int = photoList.size

        inner class PhotoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            private val imageView: ImageView = itemView.findViewById(R.id.imageView)
            private val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)
            private val descriptionTextView: TextView = itemView.findViewById(R.id.descriptionTextView)

            fun bind(photo: Photo) {
                imageView.setImageURI(Uri.parse(photo.imageUri))
                titleTextView.text = photo.title
                descriptionTextView.text = photo.description

                imageView.setOnClickListener {
                    showPhotoPopup(photo, itemView.context)
                }
            }
        }

        private fun showPhotoPopup(photo: Photo, context: Context) {
            val dialog = Dialog(context)
            dialog.setContentView(R.layout.popup_photo)

            val imageView = dialog.findViewById<ImageView>(R.id.imageView)
            val titleTextView = dialog.findViewById<TextView>(R.id.titleTextView)
            val descriptionTextView = dialog.findViewById<TextView>(R.id.descriptionTextView)

            imageView.setImageURI(Uri.parse(photo.imageUri))
            titleTextView.text = photo.title
            descriptionTextView.text = photo.description

            dialog.show()
        }
    }
}

class AddPhotoDialogFragment(
    private val photoList: MutableList<Photo>,
    private val photoAdapter: SlideshowFragment.PhotoAdapter
) : DialogFragment() {
    private lateinit var imageView: ImageView
    private lateinit var titleEditText: EditText
    private lateinit var descriptionEditText: EditText

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.dialog_add_photo, container, false)

        imageView = view.findViewById(R.id.imageView)
        titleEditText = view.findViewById(R.id.titleEditText)
        descriptionEditText = view.findViewById(R.id.descriptionEditText)

        imageView.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intent, PICK_IMAGE_REQUEST)
        }

        val saveButton = view.findViewById<Button>(R.id.saveButton)
        saveButton.setOnClickListener {
            val title = titleEditText.text.toString()
            val description = descriptionEditText.text.toString()
            val imageUri = imageView.tag as Uri
            val photo = Photo(imageUri.toString(), title, description)
            photoList.add(photo)
            photoAdapter.notifyItemInserted(photoList.size - 1)
            dismiss()
        }

        return view
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK) {
            val imageUri = data?.data
            if (imageUri != null) {
                imageView.setImageURI(imageUri)
                imageView.tag = imageUri
            }
        }
    }

    companion object {
        private const val PICK_IMAGE_REQUEST = 1
    }
}

data class Photo(
    val imageUri: String,
    val title: String,
    val description: String
)