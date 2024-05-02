package com.example.ffd.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ffd.R
import com.example.ffd.databinding.ItemRvBinding
import com.example.ffd.model.Model

class ModelAdapter(var rvAction: RvAction, var list:ArrayList<Model>): RecyclerView.Adapter<ModelAdapter.Vh>() {
    inner class Vh(var itemRvBinding: ItemRvBinding): RecyclerView.ViewHolder(itemRvBinding.root){
        fun onBind(model: Model, position: Int){
            itemRvBinding.name.text = model.name
            itemRvBinding.image.setImageResource(model.image)
            itemRvBinding.name.setOnClickListener {
                rvAction.onClick(position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(ItemRvBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(list[position],position)
    }

    interface RvAction{
        fun onClick(position: Int)
    }
}