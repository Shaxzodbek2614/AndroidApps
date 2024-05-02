package com.example.ffd.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.example.ffd.R
import com.example.ffd.adapter.ModelAdapter
import com.example.ffd.databinding.FragmentHomeBinding
import com.example.ffd.model.Model


class HomeFragment : Fragment() {
    private val binding by lazy { FragmentHomeBinding.inflate(layoutInflater) }
    lateinit var list:ArrayList<Model>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        loadData()
        val modelAdapter = ModelAdapter(object :ModelAdapter.RvAction{
            override fun onClick(position: Int) {
                findNavController().navigate(R.id.darsFragment,Bundle(position))
            }
        },list)
        binding.rv.adapter = modelAdapter

        return binding.root
    }

    fun loadData(){
        list = ArrayList()
        list.add(Model(R.drawable.padlock,"Magnit maydon. Magnit maydonni tavsiflovchi kattaliklar"))
        list.add(Model(R.drawable.padlock,"Magnit maydoni. Doimiy magnit va uning qutblari"))
        list.add(Model(R.drawable.padlock,"Magnit maydonni harakterlovchi parametrlar"))
        list.add(Model(R.drawable.padlock,"Yerning magnit maydoni"))
        list.add(Model(R.drawable.padlock,"Tokli to‘g‘ri o‘tkazgichning, halqa va g‘altakning magnit maydoni"))
        list.add(Model(R.drawable.padlock,"Magnit maydonning tokli o’tkazgichga ta’siri"))
        list.add(Model(R.drawable.padlock,"Tokli o‘tkazgichni magnit maydonda ko‘chirishda bajarilgan ish "))
        list.add(Model(R.drawable.padlock,"Tokli o‘tkazgichlarning o‘zaro ta’sir kuchi"))
        list.add(Model(R.drawable.padlock,"Bir jinsli magnit maydonida zaryadli zarraning harakati. Lorens kuchi"))
        list.add(Model(R.drawable.padlock,"Bir jinsli magnit maydonda tokli ramkaning aylanma harakati"))
        list.add(Model(R.drawable.padlock,"Elektromagnit induksiya hodisasi. Induksiya elektr yurituvchi kuch. Faradey qonuni"))
        list.add(Model(R.drawable.padlock,"O‘zinduksiya hodisasi. O‘zinduksiya EYuK. Induktivlik"))
        list.add(Model(R.drawable.padlock,"Elektromagnitlar. Elektromagnit rele"))
        list.add(Model(R.drawable.padlock,"O’zgarmas tok elektr dvigateli"))
        list.add(Model(R.drawable.padlock,"Moddalarning magnit xossalari "))
        list.add(Model(R.drawable.padlock,"Magnit maydon energiyasi"))

    }


}