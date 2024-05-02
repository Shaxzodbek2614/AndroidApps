package com.example.contact.fragments

import android.os.Bundle
import android.telephony.SmsManager
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.contact.databinding.FragmentSendMessageBinding
import com.example.contacthelper.models.MyContact

private const val ARG_PARAM1 = "myContact"

class SendMessageFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: MyContact? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getSerializable(ARG_PARAM1) as MyContact?
        }
    }

    private val binding by lazy { FragmentSendMessageBinding.inflate(layoutInflater) }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding.apply {

            binding.apply {

                tvName.text = param1?.name
                tvNumber.text = param1?.phoneNumber

                btnSend.setOnClickListener {

                    val message = edtMessage.text.toString()
                    try {

//                    val smsManager:SmsManager = requireContext().getSystemService(SmsManager::class.java)
                        val smsManager = SmsManager.getDefault() as SmsManager
                        smsManager.sendTextMessage(param1?.phoneNumber, null, message, null, null)
                        // on below line we are sending text message.
//                    smsManager.sendTextMessage(phone, null, text, null, null)

                        Toast.makeText(requireContext(), "Xabar jo'natildi", Toast.LENGTH_LONG)
                            .show()

                    } catch (e: Exception) {

                        // on catch block we are displaying toast message for error.
                        Toast.makeText(
                            requireContext(),
                            "Xabar jo'natisg=hda xatolik yuz berdi" + e.message.toString(),
                            Toast.LENGTH_LONG
                        )
                            .show()
                    }


                    edtMessage.text.clear()

                }

            }

            btnBack.setOnClickListener {
                findNavController().popBackStack()
            }

        }

        return binding.root
    }

}