package com.example.contact.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.daimajia.swipe.SwipeLayout
import com.example.contact.R
import com.example.contact.databinding.FragmentHomeBinding
import com.example.contact.databinding.ItemSwipeBinding
import com.example.contacthelper.adapters.MyContactAdapter
import com.example.contacthelper.models.MyContact

private const val TAG = "HomeFragment"

class HomeFragment : Fragment() {

    private val binding by lazy { FragmentHomeBinding.inflate(layoutInflater) }
    private lateinit var list: ArrayList<MyContact>
    private lateinit var contactAdapter: MyContactAdapter
    private lateinit var swipeBinding: ItemSwipeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        loadContacts()
        Log.d(TAG, "onCreateView: $list")
//        contactAdapter = MyContactAdapter(requireContext(), list, {
//            findNavController().navigate(
//                R.id.sendMessageFragment,
//                bundleOf("myContact" to it)
//            )
//        })
//        binding.rv.adapter = contactAdapter

//        swipeBinding = ItemSwipeBinding.inflate(layoutInflater)
//        swipeBinding.root.showMode = SwipeLayout.ShowMode.LayDown
//        swipeBinding.root.addDrag(SwipeLayout.DragEdge.Left, swipeBinding.bottomWrapper)
//
//        swipeBinding.root.addSwipeListener(object : SwipeLayout.SwipeListener {
//            override fun onClose(layout: SwipeLayout) {
//                //when the SurfaceView totally cover the BottomView.
//            }
//
//            override fun onUpdate(layout: SwipeLayout, leftOffset: Int, topOffset: Int) {
//                //you are swiping.
//            }
//
//            override fun onStartOpen(layout: SwipeLayout) {}
//            override fun onOpen(layout: SwipeLayout) {
//                //when the BottomView totally show.
//            }
//
//            override fun onStartClose(layout: SwipeLayout) {}
//            override fun onHandRelease(layout: SwipeLayout, xvel: Float, yvel: Float) {
//                //when user's hand released.
//            }
//        })


        return binding.root
    }

    @SuppressLint("Range")
    private fun loadContacts() {
        list = ArrayList()

        val contacts = requireActivity().contentResolver.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            null,
            null,
            null,
            null
        )
        while (contacts?.moveToNext() == true) {
            val name =
                contacts.getString(contacts.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))
            val number =
                contacts.getString(contacts.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
            val myContact = MyContact(name, number)
            list.add(myContact)
        }
        contacts?.close()

    }

}