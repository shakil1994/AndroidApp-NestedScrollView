package com.example.shakil.kotlinrclnested.Adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.shakil.kotlinrclnested.Model.MyModel
import com.example.shakil.kotlinrclnested.R
import com.squareup.picasso.Picasso

class MyAdapter(private val context: Context, private val modelList: List<MyModel>): RecyclerView.Adapter<MyAdapter.MyViewHolder>() {
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): MyViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.layout_item, p0, false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return modelList.size
    }

    override fun onBindViewHolder(p0: MyViewHolder, p1: Int) {
        Picasso.get().load(modelList[p1].image).into(p0.imageView)
        p0.textView.text = modelList[p1].text
    }

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        internal var imageView: ImageView
        internal var textView: TextView

        init {
            imageView = itemView.findViewById(R.id.image_view) as ImageView
            textView = itemView.findViewById(R.id.text_view) as TextView
        }
    }
}