
package com.example.contacthelper

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.ContactsContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.daimajia.swipe.SwipeLayout
import com.example.contacthelper.adapter.ContactAdapter
import com.example.contacthelper.databinding.FragmentHomeBinding
import com.example.contacthelper.databinding.ItemSwipeBinding
import com.example.contacthelper.model.MyContact


class HomeFragment : Fragment() {

    private val binding by lazy { FragmentHomeBinding.inflate(layoutInflater) }
    private lateinit var list: ArrayList<MyContact>
    private lateinit var contactAdapter: ContactAdapter
    private lateinit var swipeBinding: ItemSwipeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        if (ContextCompat.checkSelfPermission(
                requireContext(),
                android.Manifest.permission.READ_CONTACTS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // Agar ruxsat berilmagan bo'lsa, uni so'rash
            requestPermissions(
                arrayOf(Manifest.permission.READ_CONTACTS),
                100
            )
        } else {
            // Agar ruxsat berilgan bo'lsa, kontaktlarni yuklash yoki boshqa amallarni bajarish
            override fun onRequestPermissionsResult(
                requestCode: Int,
                permissions: Array<out String>,
                grantResults: IntArray
            ) {
                when (requestCode) {
                    100 -> {
                        if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                            // Ruxsat berildi, endi kontaktdan foydalanish mumkin
                        } else {
                            // Ruxsat rad etildi, ogohlantirish bering yoki boshqa chora ko'ring
                        }
                    }
                    else -> {
                        // Boshqa ruxsat kodlari uchun
                    }
                }
            }
        }
        loadContacts()
        contactAdapter = ContactAdapter(requireContext(), list) {
            findNavController().navigate(R.id.sendMessageFragment, bundleOf())
        }
        binding.rv.adapter = contactAdapter

        swipeBinding = ItemSwipeBinding.inflate(layoutInflater)
        swipeBinding.root.showMode = SwipeLayout.ShowMode.LayDown
        swipeBinding.root.addDrag(SwipeLayout.DragEdge.Left, swipeBinding.bottomWrapper)

        swipeBinding.root.addSwipeListener(object : SwipeLayout.SwipeListener {
            override fun onClose(layout: SwipeLayout) {
                //when the SurfaceView totally cover the BottomView.
            }

            override fun onUpdate(layout: SwipeLayout, leftOffset: Int, topOffset: Int) {
                //you are swiping.
            }

            override fun onStartOpen(layout: SwipeLayout) {}
            override fun onOpen(layout: SwipeLayout) {
                //when the BottomView totally show.
            }

            override fun onStartClose(layout: SwipeLayout) {}
            override fun onHandRelease(layout: SwipeLayout, xvel: Float, yvel: Float) {
                //when user's hand released.
            }
        })


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
