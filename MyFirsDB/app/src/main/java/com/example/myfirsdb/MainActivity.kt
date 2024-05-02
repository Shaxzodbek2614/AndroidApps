package com.example.myfirsdb

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import android.telephony.SmsManager
import android.widget.ImageView
import android.widget.PopupMenu
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myfirsdb.adapter.UserAdapter
import com.example.myfirsdb.databinding.ActivityMainBinding
import com.example.myfirsdb.databinding.CustomDialogBinding
import com.example.myfirsdb.db.MyDb
import com.example.myfirsdb.models.User

class MainActivity : AppCompatActivity(),UserAdapter.RvAction {
    lateinit var userAdapter: UserAdapter
    lateinit var myDb: MyDb
    private val requestCallPermission = registerForActivityResult(ActivityResultContracts.RequestPermission()){isGranted->
        if (isGranted){
            //makeCall
        }else{
            //show warning message
            makeSms("+998907570432")
        }
    }

    var changePosition = 0
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        myDb = MyDb(this)

        val myLinerLayoutManager = LinearLayoutManager(this)
        binding.rv.layoutManager = myLinerLayoutManager
        val list = myDb.showUser()

        val itemTouchHelper = ItemTouchHelper(object :ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                changePosition = viewHolder.adapterPosition
                if (direction==ItemTouchHelper.RIGHT){
                    // call
                    checkCallPermission(list[viewHolder.adapterPosition].number)
                }else{
                    // sms
                    checkSmsPermission(list[viewHolder.adapterPosition].number)
                }
            }
        })

        itemTouchHelper.attachToRecyclerView(binding.rv)
        binding.rv.addItemDecoration(DividerItemDecoration(this,myLinerLayoutManager.orientation))
        binding.btnAdd.setOnClickListener {
            val dialog = AlertDialog.Builder(this).create()
            val customDialogBinding = CustomDialogBinding.inflate(layoutInflater)
            customDialogBinding.btnSave.setOnClickListener {
                val user = User(
                    1,
                    customDialogBinding.name.text.toString(),
                    customDialogBinding.number.text.toString()
                )
                myDb.addUser(user)
                onResume()
                dialog.cancel()
            }
            dialog.setView(customDialogBinding.root)
            dialog.show()

        }

    }
    @SuppressLint("Range")
    fun getContacts(): MutableList<User> {
        val contacts = mutableListOf<User>()
        val contentResolver = contentResolver
        val cursor = contentResolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null)

        if (cursor != null) {
            while (cursor.moveToNext()) {
                val contactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID))
                val name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))

                var phoneNumber: String? = null
                if (cursor.getInt(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)) > 0) {
                    val phoneCursor = contentResolver.query(
                        ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                        null,
                        "${ContactsContract.CommonDataKinds.Phone.CONTACT_ID} = ?",
                        arrayOf(contactId),
                        null
                    )

                    if (phoneCursor != null) {
                        while (phoneCursor.moveToNext()) {
                            phoneNumber = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                        }
                        phoneCursor.close()
                    }
                }

                contacts.add(User(0,name, phoneNumber!!))
            }

            cursor.close()
        }

        return contacts
    }
    private fun checkSmsPermission(number: String){
        if (ContextCompat.checkSelfPermission(this,Manifest.permission.SEND_SMS)==PackageManager.PERMISSION_GRANTED){
            makeSms(number)
        }else{
            requestCallPermission.launch(Manifest.permission.SEND_SMS)
        }
    }

    private fun makeSms(number: String) {
       val smsManager =
           getSystemService(SmsManager::class.java)
        smsManager.sendTextMessage(number,
            null,
            "Assalomu alaykum",
            null,null)

        changePosition?.apply {
            userAdapter.notifyItemChanged(this)
        }
    }


    private fun checkCallPermission(number: String){
        if (ContextCompat.checkSelfPermission(this,Manifest.permission.CALL_PHONE)==PackageManager.PERMISSION_GRANTED){
            makeCall(number)
        }else{
            requestCallPermission.launch(Manifest.permission.CALL_PHONE)
        }
    }

    private fun makeCall(number:String) {
        Intent(Intent.ACTION_CALL, Uri.parse("tel:$number")).apply {
            startActivity(this)
        }
    }

    override fun onResume() {
        super.onResume()
        val list = myDb.showUser()
        userAdapter = UserAdapter(this,getContacts())
        binding.rv.adapter = userAdapter
    }

    override fun itemClic(user: User, imageView: ImageView) {
        val popupMenu = PopupMenu(this,imageView)
        popupMenu.inflate(R.menu.my_menu)
        popupMenu.setOnMenuItemClickListener {
            when(it.itemId){
                R.id.edit->{
                    val dialog = AlertDialog.Builder(this).create()
                    val customDialogBinding = CustomDialogBinding.inflate(layoutInflater)
                    dialog.setView(customDialogBinding.root)
                    customDialogBinding.name.setText(user.name)
                    customDialogBinding.number.setText(user.number)

                    customDialogBinding.btnSave.setOnClickListener {
                        user.name = customDialogBinding.name.text.toString()
                        user.number = customDialogBinding.number.text.toString()
                        myDb.editUser(user)
                        onResume()
                        dialog.cancel()
                    }
                    dialog.show()
                }
                R.id.delete->{
                    myDb.deleteUser(user)
                    onResume()
                }
            }

            true
        }
        popupMenu.show()
    }
}