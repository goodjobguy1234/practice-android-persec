package com.example.persecdemo.usecase.model

import android.os.Parcelable

@kotlinx.parcelize.Parcelize
data class ApodModel(
    val date: String,
    val photoUrl: String,
    val title: String,
    val desc: String
): Parcelable