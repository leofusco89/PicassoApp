package com.example.picassoapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.picassoapp.models.Photo
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_photo.view.*
import java.lang.Exception

class PhotosAdapter(private val photos: List<Photo>) :
    RecyclerView.Adapter<PhotosAdapter.PhotosHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotosHolder {
        //Cada ViewHolder indica como se va a ir mostrando cada ítem, podemos tener varios para
        //mostrar cada uno de manera diferente
        //Recibe por parámetro la vista que lo va a representar de forma visual, por este motivo, si
        //queremos mostrar cosas de manera distinta, podemos tener varios ViewHolders que esperen
        //distintas vistas

        //Acá cargamos una vista y la devolvemos en un PhotosHolder, pero podríamos cargar otro
        //tipo de item_album, un item_album_special por ejemplo, en un nuevo AlbumsSpecialHolder
        //que quizas poner un color distinto para distinguirlo
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_photo, parent, false)
        return PhotosHolder(view)
    }

    override fun getItemCount(): Int {
        return photos.size
    }

    override fun onBindViewHolder(holder: PhotosAdapter.PhotosHolder, position: Int) {
        //El holder es uno de los PhotosHolder que retornamos en el método onCreateViewHolder, así
        //que tiene una view asociada
        holder.itemView.tv_title.text = photos[position].title

        //Con servicio Picasso, cargamos la imagen dentro del Imageview del layout del ítem:
        Picasso.get().load(photos[position].url).into(holder.itemView.iv_photo,
            object : Callback {
                //Agregamos callback como en retrofit para cargar la imagen o si ocurre un
                //error (EJ: No traer la imagen por falta de memoria en el móvil) podemos cargar
                //una imagen rota o algo que indique que no se pudo traer la imagen:
                override fun onSuccess() {
                    holder.itemView.pb_photo.visibility = View.GONE
                }

                override fun onError(e: Exception?) {
                    holder.itemView.pb_photo.visibility = View.GONE
                }
            })
    }

    class PhotosHolder(view: View) : RecyclerView.ViewHolder(view)
}