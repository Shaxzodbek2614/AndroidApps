package com.example.contacthelper

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.contacthelper.adapter.ContactAdapter
import com.example.contacthelper.databinding.ActivityMainBinding
import com.example.contacthelper.model.MyContact

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

    }
}