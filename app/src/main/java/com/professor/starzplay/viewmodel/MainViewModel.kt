package com.professor.starzplay.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.professor.data_source.model.MediaItem
import com.professor.data_source.repository.MediaRepository
import com.professor.starzplay.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Rana Umer on 5/20/2025.
 *
 * Description: Application
 *
 * @version 1.0
 */


@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: MediaRepository
) : ViewModel() {

    private val _mediaState = MutableLiveData<UiState<Map<String, List<MediaItem>>>>()
    val mediaState: LiveData<UiState<Map<String, List<MediaItem>>>> = _mediaState


    init {
        loadDefaultContent()
    }

     fun loadDefaultContent() {
        viewModelScope.launch {
            _mediaState.value = UiState.Loading
            try {
                val result = repository.getTrendingMedia()
                val grouped = result.groupBy { it.media_type }.toSortedMap()
                _mediaState.value = UiState.Success(grouped)
            } catch (e: Exception) {
                _mediaState.value = UiState.Error("Failed to load trending: ${e.message}")
            }
        }
    }

    fun search(query: String) {
        viewModelScope.launch {
            _mediaState.value = UiState.Loading
            try {
                val result = repository.search(query)
                val filtered = result.filter {
                    it.poster_path != null || it.backdrop_path != null || it.profile_path != null
                }
                val grouped = filtered.groupBy { it.media_type }.toSortedMap()
                _mediaState.value = UiState.Success(grouped)
            } catch (e: Exception) {
                _mediaState.value = UiState.Error("Failed to fetch results: ${e.message}")
            }
        }
    }


}
