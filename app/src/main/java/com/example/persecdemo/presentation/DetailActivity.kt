package com.example.persecdemo.presentation

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.example.persecdemo.databinding.ActivityDetailBinding
import com.example.persecdemo.usecase.model.ApodModel
import com.example.persecdemo.utils.getParcelableTyped
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        initView(this.intent.extras?.getParcelableTyped<ApodModel>(BUNDLE_DETAIL))
    }

    private fun initView(parcelableTyped: ApodModel?) = with(binding) {
        parcelableTyped ?: return@with
        Glide.with(ivMainImage).load(parcelableTyped.photoUrl).into(ivMainImage)
        titleAppbar.text = parcelableTyped.title
        tvDate.text = parcelableTyped.date
        tvDesc.text = parcelableTyped.desc
    }

    companion object {
        const val BUNDLE_DETAIL = "BUNDLE_DETAIL"
    }
}