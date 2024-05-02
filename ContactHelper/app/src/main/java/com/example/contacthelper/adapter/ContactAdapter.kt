package com.example.contacthelper.adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.contacthelper.databinding.ItemSwipeBinding
import com.example.contacthelper.model.MyContact

class ContactAdapter(
    var context: Context, private val list: ArrayList<MyContact>,
    val sendButtonClick: () -> Unit
):RecyclerView.Adapter<ContactAdapter.Vh>() {
    inner class Vh(var itemSwipeBinding: ItemSwipeBinding):RecyclerView.ViewHolder(itemSwipeBinding.root){
        fun onBind(myContact: MyContact,position: Int){
            itemSwipeBinding.tvName.text = myContact.name
            itemSwipeBinding.tvPhone.text = myContact.number
            itemSwipeBinding.callBtn.setOnClickListener {
                val intent = Intent(Intent.ACTION_CALL)
                intent.setData(Uri.parse("tel:${myContact.number}"))
                context.startActivity(intent)
//                Toast.makeText(context, "${myContact.phoneNumber}", Toast.LENGTH_SHORT).show()
            }

            itemSwipeBinding.smsBtn.setOnClickListener {
              sendButtonClick.invoke()
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(ItemSwipeBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int =list.size

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(list[position],position)
    }
}