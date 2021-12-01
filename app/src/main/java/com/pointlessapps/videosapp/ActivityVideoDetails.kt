package com.pointlessapps.videosapp

import android.os.Bundle
import android.widget.MediaController
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import com.pointlessapps.videosapp.databinding.ActivityVideoDetailsBinding
import com.pointlessapps.videosapp.models.Video

class ActivityVideoDetails : AppCompatActivity() {

    companion object {
        const val KEY = "video"
    }

    private lateinit var binding: ActivityVideoDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityVideoDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)

        val video = intent.getParcelableExtra<Video?>(KEY)
        if (video == null) {
            finish()
            return
        }

        binding.videoPlayer.post {
            val mediaController = MediaController(binding.videoPlayer.context)
            mediaController.setAnchorView(binding.videoPlayer)
            binding.videoPlayer.setMediaController(mediaController)
            binding.videoPlayer.setVideoURI(video.videoUrl.toUri())
            binding.videoPlayer.requestFocus()
            binding.videoPlayer.start()
        }
    }

    override fun onResume() {
        super.onResume()
        binding.videoPlayer.resume()
    }

    override fun onPause() {
        binding.videoPlayer.pause()
        super.onPause()
    }

    override fun onDestroy() {
        binding.videoPlayer.stopPlayback()
        super.onDestroy()
    }
}