package com.example.myfirsdb.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.myfirsdb.databinding.ItemRvBinding
import com.example.myfirsdb.models.User

class UserAdapter(var rvAction: RvAction, var list: MutableList<User>): RecyclerView.Adapter<UserAdapter.Vh>() {
    inner class Vh(var itemRvBinding: ItemRvBinding): RecyclerView.ViewHolder(itemRvBinding.root){
        fun onBind(user: User, position: Int) {
            itemRvBinding.name.text = user.name
            itemRvBinding.number.text  = user.number
            itemRvBinding.more.setOnClickListener {
                rvAction.itemClic(user,itemRvBinding.more)
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
        fun itemClic(user: User,imageView: ImageView)
    }
}