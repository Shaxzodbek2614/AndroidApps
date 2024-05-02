
package com.example.contacthelper.adapters

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.contact.databinding.ItemSwipeBinding
import com.example.contacthelper.models.MyContact

class MyContactAdapter(
    private val context: Context,
    private val list: ArrayList<MyContact>,
    private val sendButtonClick: (MyContact) -> Unit
) :
    RecyclerView.Adapter<MyContactAdapter.Vh>() {

    inner class Vh(private val itemSwipeBinding: ItemSwipeBinding) :
        RecyclerView.ViewHolder(itemSwipeBinding.root) {
        fun onBind(myContact: MyContact) {
            itemSwipeBinding.tvName.text = myContact.name
            itemSwipeBinding.tvPhone.text = myContact.phoneNumber
            itemSwipeBinding.callBtn.setOnClickListener {
                val intent = Intent(Intent.ACTION_CALL)
                intent.setData(Uri.parse("tel:${myContact.phoneNumber}"))
                context.startActivity(intent)
//                Toast.makeText(context, "${myContact.phoneNumber}", Toast.LENGTH_SHORT).show()
            }

            itemSwipeBinding.smsBtn.setOnClickListener {
                sendButtonClick.invoke(myContact)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(ItemSwipeBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(list[position])
    }
}
