package com.professor.data_source.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.io.Serializable

/**
 * Created by Rana Umer on 5/20/2025.
 *
 * Description: com.professor.data_source.model.MediaItem
 *
 * @version 1.0
 */

@Parcelize
data class MediaItem(
    val id: Int,
    val media_type: String, // "movie", "tv", "person"
    val title: String? = null,          // For movies
    val name: String? = null,           // For TV shows or persons
    val original_title: String? = null,
    val original_name: String? = null,
    val overview: String? = null,
    val poster_path: String? = null,
    val backdrop_path: String? = null,
    val profile_path: String? = null,   // For person
    val release_date: String? = null,   // For movies
    val first_air_date: String? = null, // For TV shows
    val vote_average: Float? = null,
    val vote_count: Int? = null,
    val popularity: Float? = null,
    val adult: Boolean? = null,
    val gender: Int? = null,            // For person
    val known_for_department: String? = null,
    val known_for: List<MediaItem>? = null // Recursive field for persons
):Parcelable

data class SearchResponse(
    val results: List<MediaItem>
)
