package com.professor.data_source.repository

import com.professor.data_source.api.ApiService
import com.professor.data_source.model.MediaItem

/**
 * Created by Rana Umer on 5/20/2025.
 *
 * Description: com.professor.data_source.repository.MediaRepository
 *
 * @version 1.0
 */


class MediaRepository(private val apiKey: String,
                      private val apiService: ApiService) {
    suspend fun search(query: String): List<MediaItem> {
        return apiService.searchMedia(
            apiKey =  apiKey, query = query
        ).results
    }
    suspend fun getTrendingMedia(): List<MediaItem> {
        return apiService.getTrending(apiKey).results
    }

}
