package com.professor.data_source.api

import com.professor.data_source.Constants
import com.professor.data_source.model.SearchResponse
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by Rana Umer on 5/20/2025.
 *
 * Description: com.professor.data_source.api.ApiInterface
 *
 * @version 1.0
 */

interface ApiService {

    @GET(Constants.SEARCH_API)
    suspend fun searchMedia(
        @Query("api_key") apiKey: String, @Query("query") query: String
    ): SearchResponse

    @GET(Constants.TRENDING_API)
    suspend fun getTrending(
        @Query("api_key") apiKey: String
    ): SearchResponse

}
