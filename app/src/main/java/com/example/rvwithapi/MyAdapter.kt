package com.example.rvwithapi

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.imageview.ShapeableImageView
import com.squareup.picasso.Picasso

class MyAdapter(val context: Activity, val productArrayList: List<Product>):
RecyclerView.Adapter<MyAdapter.MyViewHolder>(){
    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var title: TextView
        var image: ShapeableImageView

        init {
            title= itemView.findViewById(R.id.ProductTitle)
            image= itemView.findViewById(R.id.ProductImage)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView= LayoutInflater.from(context).inflate(R.layout.eachitem, parent, false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return productArrayList.size
    }

    // In this onBind method we r populating the data in UI
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val curItem= productArrayList[position]
        holder.title.text= curItem.title

        //in order to image into imageView in the form url, we can use 3rd party library(here we r using picasso library)
        // in order to use picasso library here first we need to add its dependency
        Picasso.get().load(curItem.thumbnail).into(holder.image);
    }
}