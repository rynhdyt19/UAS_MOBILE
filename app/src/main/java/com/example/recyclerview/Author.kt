package com.example.recyclerview

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Context
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.recyclerview.R
import android.content.SharedPreferences

class Author : AppCompatActivity() {
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_author)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        sharedPreferences = getSharedPreferences("loginPrefs", Context.MODE_PRIVATE)

        val logoutButton: Button = findViewById(R.id.out)

        logoutButton.setOnClickListener {
            // Lakukan operasi logout di sini (misalnya, hapus sesi login atau set status login menjadi false)
            clearLoginStatus()

            // Pindahkan ke aktivitas login (MainActivity)
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finishAffinity() // Hentikan semua aktivitas di atas dan termasuk aktivitas saat ini
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun clearLoginStatus() {
        // Hapus status login dari SharedPreferences
        val editor = sharedPreferences.edit()
        editor.remove("isLogged")
        editor.apply()
    }
}
