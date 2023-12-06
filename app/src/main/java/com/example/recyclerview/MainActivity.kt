package com.example.recyclerview

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.recyclerview.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    lateinit var username : EditText
    lateinit var password : EditText
    lateinit var loginButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnsignIn.setOnClickListener(View.OnClickListener {
            if (binding.etEmail.text.toString() == "Ryan" && binding.etPassword.text.toString() == "60900121020"){
                Toast.makeText(this, "Login Berhasil", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, Home::class.java))
            } else {
                Toast.makeText(this, "Gagal Login", Toast.LENGTH_SHORT).show()
            }
        })
    }
}