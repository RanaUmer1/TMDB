package com.professor.starzplay.ui

import Constants
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.professor.data_source.model.MediaItem
import com.professor.starzplay.R
import com.professor.starzplay.databinding.ActivityMainBinding
import com.professor.starzplay.adapter.CarouselAdapter
import com.professor.starzplay.utils.NetworkChangeReceiver
import com.professor.starzplay.utils.UiState
import com.professor.starzplay.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import hideKeyboard

/**
 * Created by Rana Umer on 5/20/2025.
 *
 * Description: Application
 *
 * @version 1.0
 */

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()
    private var carouselAdapter: CarouselAdapter? = null
    private lateinit var networkChangeReceiver: NetworkChangeReceiver
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setupSearchListener()
        setupRecyclerView()
        observeViewModel()

    }


    override fun onResume() {
        super.onResume()
        networkChangeReceiver = NetworkChangeReceiver(this)
        val filter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        registerReceiver(networkChangeReceiver, filter)
    }

    override fun onPause() {
        super.onPause()
        unregisterReceiver(networkChangeReceiver)
    }
    private fun setupSearchListener() {
        binding.searchBox.setOnEditorActionListener { v, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_ACTION_DONE) {
                val query = v.text.toString().trim()
                if (query.isNotEmpty()) {
                    hideKeyboard()
                    viewModel.search(query)
                }
                true
            } else {
                false
            }
        }
    }

    private fun setupRecyclerView() {
        binding.carouselRecyclerView.layoutManager = LinearLayoutManager(this)
    }

    private fun observeViewModel() {
        viewModel.mediaState.observe(this) { state ->
            when (state) {
                is UiState.Loading -> {
                    // Show progress
                    binding.carouselRecyclerView.isVisible = false

                    binding.progressBar.isVisible = true
                }

                is UiState.Success -> {
                    binding.progressBar.isVisible = false
                    binding.carouselRecyclerView.isVisible = true
                    carouselAdapter = CarouselAdapter(state.data) { selectedItem ->
                        openDetailScreen(selectedItem)
                    }
                    binding.carouselRecyclerView.adapter = carouselAdapter
                }

                is UiState.Error -> {
                    binding.progressBar.isVisible = false
                    Toast.makeText(this, state.message, Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun openDetailScreen(item: MediaItem) {
        val intent = Intent(this, DetailsActivity::class.java)
        intent.putExtra(Constants.MEDIA_ITEM, item)
        startActivity(intent)
    }


}