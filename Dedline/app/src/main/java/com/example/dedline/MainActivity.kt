package com.example.dedline

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.dedline.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.btn.setOnClickListener {
            val text = binding.edt.text
            val dialog = AlertDialog.Builder(this)
                .setTitle("Muvaffaqiyatli")
                .setMessage("Salom $text! Xush kelibsiz!")
                .setPositiveButton("Ha"){_,_->
                    text.clear()
                }
                .setCancelable(false)
            dialog.show()
        }

    }
}