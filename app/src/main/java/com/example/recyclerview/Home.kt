package com.example.recyclerview

import android.content.Intent
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.appcompat.widget.Toolbar
import java.io.IOException

class Home : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var runnable : Runnable
    private var handler: Handler = Handler()
    private var pause:Boolean = false
    private lateinit var btnAudio : Button
    private lateinit var btnPause : Button

    var mediaPlayer : MediaPlayer? = null
    private val list= ArrayList<video>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home)
        recyclerView = findViewById(R.id.rv_video)
        recyclerView.setHasFixedSize(true)
        list.addAll(getList())
        showRecyclerList()

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        btnAudio = findViewById(R.id.button1)
        btnPause = findViewById(R.id.button2)

        btnAudio.setOnClickListener {
            if (pause) {
                mediaPlayer!!.seekTo(mediaPlayer!!.currentPosition)
                mediaPlayer!!.start()
                pause = false
                Toast.makeText(this, "Musik Dimainkan!", Toast.LENGTH_SHORT).show()
            } else {
                if (mediaPlayer != null) {
                    // Hentikan pemutaran jika sedang berlangsung
                    mediaPlayer!!.stop()
                    mediaPlayer!!.release()
                }

                mediaPlayer = MediaPlayer.create(applicationContext, R.raw.campion)
                mediaPlayer!!.start()
                Toast.makeText(this, "Musik Dimainkan!", Toast.LENGTH_SHORT).show()

                btnAudio.isEnabled = false
                btnPause.isEnabled = true

                mediaPlayer!!.setOnCompletionListener {
                    btnAudio.isEnabled = true
                    btnPause.isEnabled = false
                    Toast.makeText(this, "Dimatikan!", Toast.LENGTH_SHORT).show()
                }
            }
        }

        btnPause.setOnClickListener {
            if (mediaPlayer!!.isPlaying) {
                mediaPlayer!!.pause()
                pause = true
                btnAudio.isEnabled = true
                btnPause.isEnabled = false
                Toast.makeText(this, "Audio Dijeda!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroy() {
        // Pastikan untuk melepaskan sumber daya MediaPlayer saat aktivitas dihancurkan
        if (mediaPlayer != null) {
            mediaPlayer!!.release()
        }
        super.onDestroy()
    }

    private fun getList():ArrayList<video>{
        val gambar=resources.obtainTypedArray(R.array.data_gambar)
        val dataName=resources.getStringArray(R.array.judul_video)
        val dataDescription=resources.getStringArray(R.array.data_dekripsi)
        val videoId=resources.obtainTypedArray(R.array.video_id)
        val audioId=resources.obtainTypedArray(R.array.audio_id)
        val listvideo=ArrayList<video>()
        for (i in dataName.indices){
            val video = video(gambar.getResourceId(i, -1), dataName[i], dataDescription[i], videoId.getResourceId(i, -1), audioId.getResourceId(i, -1))
            listvideo.add(video)
        }
        return listvideo
    }
    private fun showRecyclerList(){
        recyclerView.layoutManager=LinearLayoutManager(this)
        val listadapter=ListAdapter(list)
        recyclerView.adapter=listadapter
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    // tombol action more - buat ngarahin ke hal profile ketika tombol profile diklik
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_more -> {
                val intent = Intent(this, Author::class.java)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}