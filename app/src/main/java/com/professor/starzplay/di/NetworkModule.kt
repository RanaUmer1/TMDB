package com.professor.starzplay.di

import Constants
import com.professor.data_source.api.ApiService
import com.professor.data_source.repository.MediaRepository
import com.professor.starzplay.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by Rana Umer on 5/20/2025.
 *
 * Description: Application
 *
 * @version 1.0
 */


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    fun provideApiService(): ApiService {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

    @Provides
    fun provideMediaRepository(apiService: ApiService): MediaRepository {
        return MediaRepository(
            apiKey = BuildConfig.TMDB_API_KEY,
            apiService = apiService
        )
    }
}

