package com.example.picassoapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.picassoapp.models.Photo
import com.example.picassoapp.service.ApiService
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val retrofit = Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com/")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()

        val service = retrofit.create(ApiService::class.java)
        service.getPhotos().enqueue(object : Callback<List<Photo>> {
            override fun onFailure(call: Call<List<Photo>>, t: Throwable) {
                pb.visibility = View.GONE
            }

            override fun onResponse(call: Call<List<Photo>>, response: Response<List<Photo>>) {
                pb.visibility = View.GONE
                if (response.isSuccessful) {
                    initUi(response.body()!!)
                }
            }
        })
    }

    private fun initUi(body: List<Photo>) {
        //El RecyclerView se verá como un LinearLayout (si queremos grilla, usamos el Grid y
        //modificamos el adapter acorde)
        val linearLayoutManager = LinearLayoutManager(this)
        rv_photos.layoutManager = linearLayoutManager

        //Indicamos el contenido a mostrar y el adapter para saber como mostrarlo
        val adapter = PhotosAdapter(body)
        rv_photos.adapter = adapter

        //Agregamos línea divisoria entre ítems
        val dividerItemDecoration =
            DividerItemDecoration(rv_photos.context, linearLayoutManager.orientation)
        rv_photos.addItemDecoration(dividerItemDecoration)
    }
}
