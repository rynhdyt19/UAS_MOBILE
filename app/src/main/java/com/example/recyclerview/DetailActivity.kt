package com.example.recyclerview

import android.annotation.SuppressLint
import android.content.Intent
import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView

class DetailActivity : AppCompatActivity() {
    @SuppressLint("ResourceType")
    private lateinit var recyclerView: RecyclerView
    private lateinit var runnable : Runnable
    private var handler: Handler = Handler()
    private var pause:Boolean = false
    private lateinit var btnAudio : Button
    private lateinit var btnPause : Button

    var mediaPlayer : MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        val receivedData=if (Build.VERSION.SDK_INT>=33){
            @Suppress("DEPRECATION")
            intent.getParcelableExtra<video>("shadow")
        }else{
            @Suppress("DEPRECATION")
            intent.getParcelableExtra<video>("shadow")
        }
        val gambar:ImageView=findViewById(R.id.img_gambar)
        val judul:TextView=findViewById(R.id.tv_Judul)
        val description:TextView=findViewById(R.id.tv_description)
        val playButton:ImageView=findViewById(R.id.tombol_play)
        if (receivedData!=null){
            gambar.setImageResource(receivedData.gambar)
            judul.text=receivedData.judul
            description.text=receivedData.data_deskripsi
            playButton.setOnClickListener{
                val videoIntent= Intent(this,video_Activity::class.java)
                videoIntent.putExtra("videoId",receivedData.videoId)
                startActivity(videoIntent)
            }
        }
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

                mediaPlayer = MediaPlayer.create(applicationContext, receivedData!!.audioId)
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
    }
