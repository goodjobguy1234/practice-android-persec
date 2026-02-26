package com.example.persecdemo.presentation

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.persecdemo.R
import com.example.persecdemo.databinding.ActivityMainBinding
import com.example.persecdemo.utils.formatDate
import com.example.persecdemo.utils.showDatePicker
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.time.LocalDateTime
import java.util.Calendar
import java.util.Date
import java.util.TimeZone

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()
    private val pictureListAdapter: PictureOfDaysListAdapter by lazy {
        PictureOfDaysListAdapter {
            val intent = Intent(this, DetailActivity::class.java).apply {
                putExtra(DetailActivity.BUNDLE_DETAIL, it)
            }
            startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        initView()
        initObserver()
        viewModel.state.value.let {
            viewModel.handleIntent(
                PictureListScreenIntent.GetPictureOfDay(
                    startDay = it.startDate,
                    endDay = it.endDate
                )
            )
        }
    }

    private fun initObserver() = with(binding) {
        viewModel.state
            .flowWithLifecycle(lifecycle)
            .onEach {
                viewModel.handleIntent(
                    PictureListScreenIntent.GetPictureOfDay(
                        startDay = it.startDate,
                        endDay = it.endDate
                    )
                )
                chipStartDate.text = getString(R.string.start_date_format, it.startDate)
                chipEndDate.text = getString(R.string.end_date_format, it.endDate)
                pictureListAdapter.submitList(it.list)
            }.launchIn(lifecycleScope)

        viewModel.effect
            .flowWithLifecycle(lifecycle)
            .onEach {
                when (it) {
                    PictureListScreenEvent.RenderEmptyList -> {
                        //render empty view
                    }

                    PictureListScreenEvent.ShowBottomSheetError -> {
                        //render bottomsheet
                    }
                }
            }.launchIn(lifecycleScope)
    }

    private fun initView() = with(binding) {
        chipStartDate.setOnClickListener {
            showDatePicker(supportFragmentManager) {
                viewModel.handleIntent(
                    PictureListScreenIntent.UpdateDate(
                        startDay = it
                    )
                )
            }
        }
        chipEndDate.setOnClickListener {
            showDatePicker(supportFragmentManager) {
                viewModel.handleIntent(
                    PictureListScreenIntent.UpdateDate(
                        endDay = it
                    )
                )
            }
        }
        recycleView.adapter = pictureListAdapter
    }
}