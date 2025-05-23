package com.professor.starzplay.ui

import Constants
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.professor.data_source.model.MediaItem
import com.professor.starzplay.R
import com.professor.starzplay.databinding.ActivityDetailsBinding

class DetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailsBinding
    private var mediaItem: MediaItem? = null
    val gotoNext = View.OnClickListener {
        val intent = Intent(this@DetailsActivity, PlayerActivity::class.java)
        intent.putExtra(Constants.MEDIA_ITEM, mediaItem)
        startActivity(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        mediaItem = intent.getParcelableExtra(Constants.MEDIA_ITEM)
        mediaItem?.let { binding.bindMediaItem(it) }

    }


    private fun ActivityDetailsBinding.bindMediaItem(mediaItem: MediaItem) {
        // Load image based on media type
        val imageUrl = when (mediaItem.media_type) {
            "person" -> mediaItem.profile_path
            else -> mediaItem.backdrop_path ?: mediaItem.poster_path
        }
        imageUrl?.let {
            val fullUrl = "https://image.tmdb.org/t/p/w500$it"
            Glide.with(binding.itemImageView.context).load(fullUrl)
                .placeholder(R.drawable.place_holder_bg).into(binding.itemImageView)

        } ?: run {
            itemImageView.setImageResource(R.drawable.place_holder_bg)
        }

        // Title / name
        itemTitleTextView.text = when (mediaItem.media_type) {
            "movie" -> mediaItem.title ?: "No Title"
            "tv" -> mediaItem.name ?: "No Name"
            "person" -> mediaItem.name ?: "No Name"
            else -> "Unknown"
        }

        // Release / air date
        releaseDateTextView.text = when (mediaItem.media_type) {
            "movie" -> mediaItem.release_date ?: ""
            "tv" -> mediaItem.first_air_date ?: ""
            else -> ""
        }

        // Ratings & popularity
        voteAverageTextView.text = mediaItem.vote_average?.let { "⭐ %.1f".format(it) } ?: "⭐ N/A"
        voteCountTextView.text = mediaItem.vote_count?.let { "($it votes)" } ?: ""
        popularityTextView.text = mediaItem.popularity?.let { "Popularity: %.2f".format(it) } ?: ""

        // Description / overview
        itemDescriptionTextView.text = mediaItem.overview ?: "No Description Available"

        // Person specific info
        if (mediaItem.media_type == "person") {
            personInfoLayout.visibility = View.VISIBLE
            genderTextView.text = when (mediaItem.gender) {
                1 -> "Gender: Female"
                2 -> "Gender: Male"
                else -> "Gender: Unknown"
            }
            knownForDeptTextView.text = "Known for: ${mediaItem.known_for_department ?: "N/A"}"
        } else {
            personInfoLayout.visibility = View.GONE
        }

        // Playback button visible only for movie or tv
        playbackButton.visibility =
            if (mediaItem.media_type == "movie" || mediaItem.media_type == "tv") {
                View.VISIBLE
            } else {
                View.GONE
            }

        playbackButton.setOnClickListener(gotoNext)
        itemImageView.setOnClickListener(gotoNext)
    }


}